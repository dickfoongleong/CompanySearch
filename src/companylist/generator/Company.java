package companylist.generator;

/**
 * Company Instance class.
 * 
 * @author Dickfoong
 *
 */
public class Company implements Comparable<Company> {

  /**
   * Company name.
   */
  private String name;

  /**
   * Company street.
   */
  private String street;

  /**
   * Company floor of the building.
   */
  private String floor;

  /**
   * Company city.
   */
  private String city;

  /**
   * Company state.
   */
  private String state;

  /**
   * Company zip code.
   */
  private int zip;

  /**
   * Empty instance constructor.
   */
  public Company() {

  }

  /**
   * Company instances with full informations.
   * 
   * @param name Company name.
   * @param street Company street.
   * @param floor Company floor.
   * @param city Company city.
   * @param state Company state.
   * @param zip Company zip code.
   */
  public Company(String name, String street, String floor, String city, String state, int zip) {
    this.name = name;
    this.street = street;
    this.floor = floor;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * Company Instance without floor.
   * 
   * @param name Company name.
   * @param street Company street.
   * @param city Company city.
   * @param state Company state.
   * @param zip Company zip code.
   */
  public Company(String name, String street, String city, String state, int zip) {
    this.name = name;
    this.street = street;
    this.floor = "\0";
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * Get the name of company.
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the street of company.
   * 
   * @return
   */
  public String getStreet() {
    return this.street;
  }

  /**
   * Get the floor of company.
   * 
   * @return
   */
  public String getFloor() {
    return this.floor;
  }

  /**
   * Get the city of company.
   * 
   * @return
   */
  public String getCity() {
    return this.city;
  }

  /**
   * Get the state of company.
   * 
   * @return
   */
  public String getState() {
    return this.state;
  }

  /**
   * Get the zip code of company.
   * 
   * @return
   */
  public int getZip() {
    return this.zip;
  }

  /**
   * Get the full address of company.
   * 
   * @return
   */
  public String getFullAddress() {
    if (this.floor.equals("\0")) {
      return this.street + ", " + this.city + ", " 
          + this.state + " " + this.zip;
    } else {
      return this.street + ", " + this.floor + ", " + this.city + ", "
          + this.state + " " + this.zip;
    }
  }

  public String toString() {
    return this.name + "," + this.street + "," + this.floor
        + "," + this.city + "," + this.state + "," + this.zip;
  }
  
  @Override
  public int compareTo(Company company2) {
    if (this.name.equalsIgnoreCase(company2.getName())) {
      String fullAddress = getFullAddress();
      if (fullAddress.equalsIgnoreCase(company2.getFullAddress())) {
        return 0;
      } 
    }
    return 1;
  }
}
