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
			$unwind: {
				path: "$content.entities.provenance"
			}
		},

		// Stage 4
		{
			$count: "count"
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
