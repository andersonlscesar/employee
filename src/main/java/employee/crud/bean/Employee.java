package employee.crud.bean;

public class Employee {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Employee() {
    }

    public Employee(String address, String phone, String email, String name) {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public Employee(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
