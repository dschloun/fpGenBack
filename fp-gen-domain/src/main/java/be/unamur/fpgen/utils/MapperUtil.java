package be.unamur.fpgen.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class to map a list of objects to another list of objects
 * Typically to map a list of entities objects to domain objects
 */
public class MapperUtil {

    private MapperUtil(){
    }

    public static <T, E> List<E> mapList(final List<T> sourceList, final Function<T,E> mapListFunction){
        if(CollectionUtils.isEmpty(sourceList)){
            return Collections.emptyList();
        }
        return sourceList
                .stream()
                .map(mapListFunction)
                .toList();
    }

    public static <T, E> Set<E> mapSet(final Set<T> sourceSet, final Function<T,E> mapSetFunction){
        if(CollectionUtils.isEmpty(sourceSet)){
            return Collections.emptySet();
        }
        return sourceSet
                .stream()
                .map(mapSetFunction)
                .collect(Collectors.toSet());
    }
}
