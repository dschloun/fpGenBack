#README :
# This script executes the docker image harbor.afeliodev.local/fcsd/apigenerator:latest
# This image is supposed to be published on registry but can be built manually if needed. See /fcsd-daenae/infrastructure/docker/apigenerator/README.MD
# This script must be in the same directory than all APIs and executed from a terminal that is also in that directory (pwd)
# eg.:
# open a terminal
# cd /Users/jdm/sources/fcsd/fcsd-daenae/architecture/api
# ./generate
#
# By default sources are generated in a /generated folder locally and sources are not packaged nor published. Useful to ensure that generated code is correct
# Publishing y/N will be prompted. If YES is chosen, generated sources will be packaged and published but won't be copied locally

openapiGeneratorVersion="5.0.1"
openapiGeneratorJavaVersion="5.0.1"
openapiGeneratorDotnetClientVersion="4.3.1"

outputApiFolder="/api/api-generated"
outputServerFolder="/api/server-generated"
outputClientFolder="/api/client-generated"
outputFrontFolder="/api/front-generated"

#npmRegistry="https://afelio-belgium.pkgs.visualstudio.com/AfelioRH/_packaging/AfelioHR/npm/registry/"
npmRegistry="http://fcsd-nexus.afeliodev.local/repository/npm-private/"
nugetRegistry="http://fcsd-nexus.afeliodev.local/repository/nuget-hosted/"
mavenRegistry="https://afelio-belgium.pkgs.visualstudio.com/AfelioRH/_packaging/AfelioHR/maven/v1"


# !!!!!!!!!!!!!!!!!!!!! DO NOT COMMIT !!!!!!!!!!!!!!!!!!!!!!!!
#nugetToken="f6768fab-7ce2-3fac-94e7-e5ceace955df"
azureToken="v7rmo7zmpm5gziujsnhvgeiul3subbzdsn4l7eprfglvdtiaarlq"
registryId="AfelioHR"
registryLogin="admin"
registryPassword="@felio4FCSD2020"
# !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

function lowercase()
{
    local localStr="$@"
    local localOutput=$(tr '[A-Z]' '[a-z]'<<<"${localStr}")
    echo $localOutput
}

function getVersion()
{
  # Read version from the swagger file content
  # e.g. : version: "1.1.0"
  local localVersion=$(cat $PWD/$1 | awk '/version:/{print $NF; exit}')

  # Remove all special characters (carriage returns, ...)
  localVersion="${localVersion%%[[:cntrl:]]}"

  # Remove all "
  localVersion="${localVersion//\"}"

  # Remove all '
  localVersion="${localVersion//\'}"

  echo $localVersion
}

function buildDotnetServerIgnore() {
  # tabulations in this function are mandatory, do not remove them!

  mkdir -p $PWD/server-generated && cat <<- EOF > $PWD/server-generated/.openapi-generator-ignore
	src/**/Controllers/DefaultApi.cs
	src/**/Models/*AllOf.cs
	src/**/Models/*AllOfDto.cs
	EOF
}

function buildDotnetServerConfig() {
  # tabulations in this function are mandatory, do not remove them!

  mkdir -p $PWD/config-generated && cat <<- EOF > $PWD/config-generated/aspnetcore.yaml
	inputSpec: $outputApiFolder/openapi/openapi.yaml
	generatorName: aspnetcore
	templateDir: /api/templates/aspnetcore
	outputDir: $outputServerFolder
	EOF

  if [[ $options == *"ADDMODELSUFFIX"* ]];
  then
    cat <<- EOF >> $PWD/config-generated/aspnetcore.yaml
		modelNameSuffix: Dto
		EOF
  fi

  cat <<- EOF >> $PWD/config-generated/aspnetcore.yaml
	typeMappings:
	  file: "IFormFile"
	importMappings:
	  IFormFile: "Microsoft.AspNetCore.Http.IFormFile"
	EOF

  cat <<- EOF >> $PWD/config-generated/aspnetcore.yaml
	additionalProperties:
	  aspnetCoreVersion: "3.1"
	  packageName: "$publishPackageName"
	  packageVersion: "$version"
	  generateBody: "false"
	  classModifier: "abstract"
	  operationModifier: "abstract"
	  isLibrary: "true"
	  buildTarget: "library"
	  enumNameSuffix: ""
	  enumValueSuffix: ""
	EOF

  if [[ $options == *"ADDRESULTTASK"* ]];
  then
    cat <<- EOF >> $PWD/config-generated/aspnetcore.yaml
		  operationResultTask: true
		EOF
  fi

  if [[ $references == *"ADDRESS.COMMON"* ]];
  then
    cat <<- EOF >> $PWD/config-generated/aspnetcore.yaml
		  useAddressCommon: "true"
		  addressCommonVersion: "$(getVersion 'address-domain.yaml')"
		EOF
  fi

  if [[ $references == *"BENEFIT.COMMON"* ]];
  then
    cat <<- EOF >> $PWD/config-generated/aspnetcore.yaml
		  useBenefitCommon: "true"
		  benefitCommonVersion: "$(getVersion 'benefit-domain.yaml')"
		EOF
  fi
}

function generateOpenapi() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Generating global OpenApi yaml file for $apiFile $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
  rm -rf '$outputApiFolder';
  java -jar backend/openapi-generator-cli-'$openapiGeneratorVersion'.jar generate -g openapi-yaml -i /api/'$apiFile' -o '$outputApiFolder';
  '
}

function generateDotnetServerCode() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Generating .NET server of API $apiFile $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  buildDotnetServerIgnore
  buildDotnetServerConfig

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
  java -jar backend/openapi-generator-cli-'$openapiGeneratorVersion'.jar batch --clean /api/config-generated/aspnetcore.yaml;

  cp /api/templates/NuGet.config '$outputServerFolder'/src/'$publishPackageName'/;
  cd '$outputServerFolder'/src/'$publishPackageName';

  dotnet pack -c Release -o out -p:PackageVersion='$version';
  '
}

function generateJavaServerCode() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Generating JAVA server of API  $apiFile $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c 'java -jar backend/openapi-generator-cli-'$openapiGeneratorJavaVersion'.jar generate -g spring -i '$outputApiFolder'/openapi/openapi.yaml -o '$outputServerFolder' --global-property skipFormModel=false --additional-properties=groupId='$groupId',artifactId="'$artifactId'",apiPackage="'$publishPackageName'",modelPackage="'$publishModelPackageName'",artifactVersion="'$version'",library=spring-boot,templateDirectory=/api/template/spring,java8=true,dateLibrary=java8,serializableModel=true,serializationLibrary=jackson,interfaceOnly=true,skipDefaultInterface=true,prependFormOrBodyParameters=true,useTags=true,bigDecimalAsString=true,booleanGetterPrefix=is,useBeanValidation=false,hideGenerationTimestamp=true,openApiNullable=false;'
}

function generateJavascriptClientCode() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Generating JS client of API $apiFile $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
    cd front && npm run prepare-workspace -- --apiName='$publishPackageName' --apiVersion='$version' --apiFile=api-generated/openapi/openapi.yaml --registry='$npmRegistry';
    npm run package;
    mkdir -p '$outputFrontFolder';
    cp -r dist/* '$outputFrontFolder';
  '
}

function generateDotnetClientCode() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Generating .NET client of API $apiFile $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
    java -jar backend/openapi-generator-cli-'$openapiGeneratorDotnetClientVersion'.jar generate -g csharp-netcore -i '$outputApiFolder'/openapi/openapi.yaml --model-package Models -o '$outputClientFolder' --additional-properties=targetFramework=netcoreapp3.1,packageName="'$publishPackageName'",packageVersion="'$version'",operationIsAsync=false,generateBody=true,classModifier=abstract,operationModifier=abstract,enumNameSuffix,enumValueSuffix,isLibrary=true,buildTarget=library,optionalEmitDefaultValues=true;

    cd '$outputClientFolder'/src/'$publishPackageName';
    dotnet pack -c Release -o out -p:PackageVersion='$version';
  '
}

function publishNugetPackage() {
  outputFolder=$1

  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Publishing NuGet package $publishPackageName $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
    cd '$outputFolder'/src/'$publishPackageName';
    dotnet nuget push out/'$publishPackageName'.'$version'.nupkg -k '$azureToken' -s '$nugetRegistry';
  '
}

function publishMavenPackage() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Publishing Maven package $publishPackageName $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  read -p "Login : " registryLogin
  echo -n "Password : "
  read -s registryPassword

  docker run --rm -it -v "$PWD:/api" -v "$PWD/maven-generated:/root/.m2/repository" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
    cd '$outputServerFolder';
    mvn package;
    mvn -e deploy:deploy-file \
     -Dfile=target/'$jarFileName' \
     -DgroupId='$groupId' \
     -DartifactId='$artifactId' \
     -Dversion='$version' \
     -Dpackaging=jar \
     -DgeneratePom=true \
     -DrepositoryId=AfelioHR \
     -Durl='$mavenRegistry' \
     -Drepo.id=AfelioHR \
     -Drepo.username='$registryLogin' \
     -Drepo.password='$registryPassword';
  '
}

function publishNpmPackage() {
  echo
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  echo "Publishing Npm package $publishPackageName $version..."
  echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  docker run --rm -it -v "$PWD:/api" harbor.afeliodev.local/fcsd/apigenerator:latest /bin/sh -c '
    cd front && npm run prepare-workspace -- --apiName='$publishPackageName' --apiVersion='$version' --apiFile=api-generated/openapi/openapi.yaml --registry='$npmRegistry';
    npm run package && npm login --registry='$npmRegistry';
    npm run publish;
  '
}

function generate() {
  apiFile=$1
  packageName=$2
  publish=$3
  generation=$4
  options=$5
  references=$6

  if [[ -n "$7" ]];
   then
    openapiGeneratorVersion=$7
    openapiGeneratorDotnetClientVersion=$7
  fi

  if test -f "$apiFile";
    then
      version=$(getVersion $apiFile)

      if [[ $publish == [Yy] ]] && ([[ $generation == *"SERVER.COMMON"* ]] || [[ $generation == *"SERVER.NET"* ]] || [[ $generation == *"CLIENT.NET"* ]]); then
        read -p "Nuget token : " azureToken
      fi

      generateOpenapi

      if [[ $generation == *"SERVER.COMMON"* ]]; then
        publishPackageName="${packageName}Common"

        generateDotnetServerCode

        if [[ $publish == [Yy] ]]; then
          publishNugetPackage $outputServerFolder
        fi
      fi

      if [[ $generation == *"SERVER.NET"* ]]; then
        publishPackageName="${packageName}Api"

        generateDotnetServerCode

        if [[ $publish == [Yy] ]]; then
          publishNugetPackage $outputServerFolder
        fi
      fi

      if [[ $generation == *"SERVER.JAVA"* ]]; then
        publishPackageName=$(lowercase "be.afelio.${packageName}.web.generated")
        publishModelPackageName="${publishPackageName}.model"
        artifactId=$(lowercase "afelio-${packageName}-api")
        groupId="be.afelio"
        jarFileName="${artifactId}-${version}.jar"

        generateJavaServerCode

        if [[ $publish == [Yy] ]]; then
          publishMavenPackage
        fi
      fi

      if [[ $generation == *"CLIENT.JS"* ]]; then
        publishPackageName="${packageName}Api"

        generateJavascriptClientCode

        if [[ $publish == [Yy] ]]; then
          publishNpmPackage
        fi
      fi

      if [[ $generation == *"CLIENT.NET"* ]]; then
        publishPackageName="${packageName}Client"

        generateDotnetClientCode

        if [[ $publish == [Yy] ]]; then
          publishNugetPackage $outputClientFolder
        fi
      fi
    else
      echo "API file $apiFile doesn't exist"
      echo "Are you sure you are executing the generate.sh from a terminal that is in /api directory ?"
  fi
}

echo "-----------------------------------"
echo "A P I  G E N E R A T O R"
echo "-----------------------------------"
echo "Which API ?"
echo "a : Afelio HR"
read -n 1 -p "API : " api
echo ""
echo "-----------------------------------"
echo "Generate sources only by default"
read -n 1 -p "Publish ? y/N : " publish
echo ""

case $api in
#    "a" ) generate "afelio-hr-api.yaml" "hr" $publish "SERVER.JAVA";;
#    "a" ) generate "afelio-hr-api.yaml" "hr" $publish "SERVER.JAVA_CLIENT.JS";;
    "a" ) generate "afelio-hr-api.yaml" "hr" $publish "CLIENT.JS";;
    * ) echo "Unknown API";;
esac