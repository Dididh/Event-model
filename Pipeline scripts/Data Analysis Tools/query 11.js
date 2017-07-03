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
			$match: {$and : [{"content.entities.types.type" : {$ne: "person"}}, {"content.entities.types.type" : {$ne: "Person"}}, {"content.entities.types.type" : {$ne: "place"}}, {"content.entities.types.type" : {$ne: "Place"}}]}
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
