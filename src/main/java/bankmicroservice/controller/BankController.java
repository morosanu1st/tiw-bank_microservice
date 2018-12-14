package bankmicroservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import bankmicroservice.dao.TransactionDao;
import bankmicroservice.domains.Transaction;

@RestController
@CrossOrigin
public class BankController {

	@Autowired
	private TransactionDao txdao;

	@RequestMapping(method = RequestMethod.POST, value = "/transaction")
	public ResponseEntity<String> confirmTransaction(@RequestBody String jsonString) {
		Transaction tx = null;
		Gson gson = new Gson();
		JsonObject j = gson.fromJson(jsonString, JsonObject.class);
		String d = j.get("expiration").getAsString();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			tx = gson.fromJson(jsonString, Transaction.class);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Date test;
		try {
			test = formatter.parse(d);
			tx.setExpiration(test);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (tx.getCard_number().length() != 16) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (Integer
				.parseInt(tx.getCard_number().substring(tx.getCard_number().length() - 2, tx.getCard_number().length()))
				% 4 != 0) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (tx.getCv2() < 100 || tx.getCv2() >= 1000) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Date now = new Date();
		if (tx.getExpiration().before(now)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		tx.setTransactionid(0);
		tx.setTx_date(now);
		txdao.save(tx);
		Transaction t = txdao.findTopByOrderByTransactionidDesc();

		return new ResponseEntity<String>(t.getTransactionid() + "", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/transaction")
	public ResponseEntity<Iterable<Transaction>> getTransactions() {
		return new ResponseEntity<>(txdao.findAll(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/transaction/{id}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable int id) {
		return new ResponseEntity<>(txdao.findByTransactionid(id),HttpStatus.OK);
	}
}
