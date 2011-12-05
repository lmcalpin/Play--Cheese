package play.modules.cheese;

public class CreditCard {
    private String firstName;
    private String lastName;
    private String number;
    private int expireMonth;
    private int expireYear;
    private String code;
    private String zip;

    public CreditCard(String firstName, String lastName, String number, int expireMonth, int expireYear, String code, String zip) {
        this(firstName, lastName, number, expireMonth, expireYear);
        this.code = code;
        this.zip = zip;
    }
    
    public CreditCard(String firstName, String lastName, String number, int expireMonth, int expireYear) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
    }
    
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getNumber() {
        return number;
    }
    public int getExpireMonth() {
        return expireMonth;
    }
    public int getExpireYear() {
        return expireYear;
    }
    public String getCode() {
        return code;
    }
    public String getZip() {
        return zip;
    }
}
