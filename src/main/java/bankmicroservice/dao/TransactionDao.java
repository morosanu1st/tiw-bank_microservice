package bankmicroservice.dao;

import org.springframework.data.repository.CrudRepository;

import bankmicroservice.domains.Transaction;

public interface TransactionDao extends CrudRepository<Transaction, Long> {
	public Transaction findTopByOrderByTransactionidDesc();
	public Transaction findByTransactionid(int transactionid);
}
