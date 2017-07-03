db.nafEntities.aggregate(

	// Pipeline
	[
		// Stage 1
		{
			$unwind: {
			    path : "$List of entities"
			}
		},

		// Stage 2
		{
			$match: {
				"_id" : "_id",
				"Number of entities" : "quid",
				"List of entities" : "quo"
			}
		},

	]

	// Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

);
