db.entities.aggregate(

	// Pipeline
	[
		// Stage 1
		{
			$match: {
			    "documentType": "aggregated-ner"
			}
		},

		// Stage 2
		{
			$unwind: {
			    path : "$content.entities",
			}
		},

		// Stage 3
		{
			$group: {
				_id : "$content.page_name",
				"Number of entities" : {$sum : 1},
				"List of entities" : {$push : "$content.entities.label"}
			}
		},

		// Stage 4
		{
			$project: {
			   page : "$_id",
			   "Number of entities": "$Number of entities",
			   "List of entities" : "$List of entities"
			}
		},

		// Stage 5
		{
			$unwind: {
			    path : "$List of entities"
			}
		},
	],

	// Options
	{
		cursor: {
			batchSize: 50
		}
	}

	// Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

);
