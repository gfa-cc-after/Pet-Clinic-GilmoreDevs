package model;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

public class RegisterUser {
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String password;

  public RegisterUser(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public RegisterUser(Faker faker, String password) {
    Name name = faker.name();
    this.firstName = name.firstName();
    this.lastName = name.lastName();
    this.email = String.format("%s.%s@gmail.com", firstName, lastName);
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
