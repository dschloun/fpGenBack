I've create that because I didn't get the right result from a request.
But actually it was because in a request:
datasetId not in (...)
if in DB dataset_id is null => it wont be in the result and I need it so I had or datasetId is null.
