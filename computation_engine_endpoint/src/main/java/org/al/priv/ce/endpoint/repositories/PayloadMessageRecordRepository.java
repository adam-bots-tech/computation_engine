package org.al.priv.ce.endpoint.repositories;

import org.al.priv.ce.endpoint.entities.PayloadMessageRecord;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayloadMessageRecordRepository extends CassandraRepository<PayloadMessageRecord, Long> {

}
