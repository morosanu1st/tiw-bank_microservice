package bankmicroservice.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="TRANSACTION")
@NamedQuery(name="Transaction.findAll", query="SELECT b FROM Transaction b")
public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionid;
	
	private String card_number;
	
	private int cv2;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiration;
	
	private int ammount;
	
	private int guestid;
	
	private int hostid;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tx_date;
	
	public Transaction() {
		
	}

	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionId) {
		this.transactionid = transactionId;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public int getCv2() {
		return cv2;
	}

	public void setCv2(int cv2) {
		this.cv2 = cv2;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public int getGuestid() {
		return guestid;
	}

	public void setGuestid(int guestid) {
		this.guestid = guestid;
	}

	public int getHostid() {
		return hostid;
	}

	public void setHostid(int hostid) {
		this.hostid = hostid;
	}

	public Date getTx_date() {
		return tx_date;
	}

	public void setTx_date(Date tx_date) {
		this.tx_date = tx_date;
	}	
	
}
