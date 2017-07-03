// Stages that have been excluded from the aggregation pipeline query
__3tsoftwarelabs_disabled_aggregation_stages = [

	{
		// Stage 5 - excluded
		stage: 5,  source: {
			$group: {
			    "_id": "$content.entities.label"
			}
		}
	},
]

db.entities.aggregate(

	// Pipeline
	[
		// Stage 1
		{
			$match: {
			    documentType: "aggregated-ner"
			}
		},

		// Stage 2
		{
			$unwind: {
			    path : "$content.entities"
			}
		},

		// Stage 3
		{
			$match: {
			    "content.entities.provenance": {$nin : ["semitags", "textrazor", "thd"]}
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
