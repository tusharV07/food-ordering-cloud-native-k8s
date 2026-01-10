package com.tsv.order_service.service;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import com.tsv.order_service.entity.Sequence;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SequenceGenerator {
	private final MongoOperations mongoOps;
	
	public Long getNextSequence() {
		Sequence sequence=mongoOps.findAndModify(
				new Query(where("_id").is("sequence")), 
				new Update().inc("sequenceNumber",1),
				options().returnNew(true).upsert(true),
				Sequence.class);
		return sequence.getSequenceNumber();
	}
}
