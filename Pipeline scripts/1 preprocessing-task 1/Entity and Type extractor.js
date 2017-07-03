db.entities.aggregate(

	// Pipeline
	[
		// Stage 1
		{
			$match: {
			    documentType : "aggregated-ner"
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
			    "content.entities.startOffset" : {$gte: 957},
			    "content.entities.endOffset" : {$lte: 8240},
			    "content.page_name" : "https://nl.wikipedia.org/wiki/Hannie_Schaft",
			    "content.section_index" : "2"
			    
			}
		},

		// Stage 4
		{
			$unwind: {
			    path : "$content.entities.types"
			}
		},

		// Stage 5
		{
			$project: {
			    _id : "$_id",
			    entity : "$content.entities.label",
			    type : "$content.entities.types.type"
			}
		},

		// Stage 6
		{
			$match: {
			    $or:[
			      {type : "place"},
			      {type : "Place"},
			      {type : "location"},
			      {type : "person"},
			      {type : "Person"},
			      {type : "organization"},
			      {type : "process"},
			      {type : "Process"},      
			      {type : "Month"}
			      ]
			}
		},

		// Stage 7
		{
			$group: {
			    _id : "$entity",
			    type : {$addToSet : "$type"}
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
