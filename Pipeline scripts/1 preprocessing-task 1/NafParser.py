#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import print_function
from KafNafParserPy import *

if __name__ == '__main__':
	filename = 'C:\\Users\\ijffdrie\\Downloads\\wiki_naf_out.tar\\wiki_naf_out\\wikiout\\1633__Jan Brasser (verzetsstrijder).naf'
	# We create the parser object
	my_parser = KafNafParser(filename)
	tokens = []
	terms = []
	entity_targets = []
	entities = []
	term_targets = []
	coreference_targets = []
	term_coref_targets = []
	entity_texts = []
	timex_targets = []
	term_timex_targets = []
	for text_obj in my_parser.get_tokens():
		if(int(text_obj.get_offset()) > 185 and int(text_obj.get_offset()) < 252):
			tokens.append(text_obj.get_id())
	for term_obj in my_parser.get_terms():
		for target in term_obj.get_span().get_span_ids():
			if(target in tokens):
				terms.append(term_obj.get_id())
	
	#Entities
	for entity_obj in my_parser.get_entities():
		for reference in entity_obj.get_references():
			span = reference.get_span()
			for target in span.get_span_ids():
				if(target in terms):
					entity_targets.append(target)
					entities.append(entity_obj)
	for term_obj in my_parser.get_terms():
		if(term_obj.get_id() in entity_targets):
			for target in term_obj.get_span().get_span_ids():
				term_targets.append(target)
	for text_obj in my_parser.get_tokens():
		if(text_obj.get_id() in term_targets):
			entity_texts.append(text_obj.get_text())
			
	#Coreferences
	for coreference_obj in my_parser.get_corefs():
		for span in coreference_obj.get_spans():
			for target in span.get_span_ids():
				if(target in terms):
					coreference_targets.append(target)
					entities.append(coreference_obj)
	for term_obj in my_parser.get_terms():
		if(term_obj.get_id() in coreference_targets):
			for target in term_obj.get_span().get_span_ids():
				term_coref_targets.append(target)
	for text_obj in my_parser.get_tokens():
		if(text_obj.get_id() in term_coref_targets):
			entity_texts.append(text_obj.get_text())
	
	#timex
	skipOne = 'true'
	for timex_obj in my_parser.get_timeExpressions():
		if (skipOne == 'true'):
			skipOne = 'false'
		else:
			span = timex_obj.get_span()
			for target in span.get_span_ids():
				if(target in tokens):
					timex_targets.append(target)
					entities.append(timex_obj)
	for text_obj in my_parser.get_tokens():
		if(text_obj.get_id() in timex_targets):
			entity_texts.append(text_obj.get_text())
	
	for x in range(len(entity_texts)):
		print(entity_texts[x],'\t',entities[x].get_type())