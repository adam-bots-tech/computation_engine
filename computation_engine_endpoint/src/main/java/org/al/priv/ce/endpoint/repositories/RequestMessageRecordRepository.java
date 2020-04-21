package org.al.priv.ce.endpoint.repositories;

import org.al.priv.ce.endpoint.entities.RequestMessageRecord;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestMessageRecordRepository extends CassandraRepository<RequestMessageRecord, Long> {

}
