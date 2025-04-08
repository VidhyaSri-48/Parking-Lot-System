import java.util.*;

class Customer{
    static int customerID_Counter = 0;
    int customerID = 0;
    String name;
    long mobile;

    Customer(String name, long mobile){
        this.customerID  = ++customerID_Counter;
        this.name = name;
        this.mobile = mobile;
    }
    static HashMap<Integer, Customer>custMap = new HashMap<>();
    
    static void addCustomer(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Customer Name:");
        String name = sc.next();
        System.out.println("Enter Customer Mobile.no:");
        long mobile = sc.nextLong();    //validate

        Customer customer = new Customer(name, mobile);
        custMap.put(customer.customerID, customer);
    }
    static void displayCustomer(){
        System.out.println("Customer Details");
        for(Map.Entry<Integer, Customer> entry: custMap.entrySet()){
            
            Customer cust = entry.getValue();
            System.out.println("Customer ID:" + cust.customerID);
            System.out.println("Customer Name:" + cust.name);
            System.out.println("Mobile.no:" + cust.mobile);
            System.out.println("------------------------------------");
        }
    }
}

class Car{
    static int carID_Counter = 0;
    int carID ;
    String carName;
    String carNumber;
    double startTime;
    ParkingLot parkLot;
    int lot;
    

    Car(String carName, String carNumber, ParkingLot parkLot, double startTime){
        this.carID = ++carID_Counter;
        this.carName = carName;
        this.carNumber = carNumber;
        this.parkLot =  parkLot;  
        this.startTime = startTime;
    }
    static HashMap<Integer, Car>carMap = new HashMap<>();

    static void addCar(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the lot id from table");
        ParkingLot.displayAvailableLot();
        System.out.println();
        int lot = sc.nextInt();
        if(ParkingLot.totalCarLot == 0){
            System.out.println("Parking lot is house full! Wait for sometime");
        }
        if(ParkingLot.checkAvalability(lot)){
            ParkingLot.totalCarLot -= 1;   // reduce total car count
            ParkingLot.lotList.set(lot, true);
        }
        else{
            System.out.println("Already booked! Choose other lotid");
            return;
        }
        
        System.out.println("Enter Car Name: ");
        String carName = sc.next();
        System.out.println("Enter Car Number: ");
        String carNumber = sc.next();
        System.out.println("Enter start time: ");
        double startTime = sc.nextDouble();
        ParkingLot parkLot = new ParkingLot(null, null, 0.0, 0.0);
        Car car = new Car(carName, carNumber, parkLot, 0.0);
        carMap.put(lot, car);
        
        
    }

    static void displayCar(){
        System.out.println("Car Details");
        for(Map.Entry<Integer, Car> entry: carMap.entrySet()){
            
            Car car = entry.getValue();
            if(car.lot == 0){
                System.out.println("Park car to display");
                System.out.println("------------------------------------");
                return;
            }
            System.out.println("CarLot Number:" + car.lot);
            System.out.println("Car Name:" + car.carName);
            System.out.println("Mobile.no:" + car.carNumber);
            System.out.println("------------------------------------");
        }
    }

    static void removeCar(){
        ParkingLot.towAway();
    }
}

class ParkingLot{
    Car car;
    Customer customer;
    double totalAmount;
    double endTime;

    ParkingLot(Car car, Customer customer, double totalAmount, double endTime){
        this.car = car;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this. endTime = endTime;
    }
    static int totalCarLot = 50;   // total lot available
    static List<Boolean>lotList = new ArrayList<>();
    static{
        for(int i=0;i<50;i++){
            lotList.add(false);
        }
    }
    
    
    static void displayAvailableLot(){
        for(int i=0;i<50;i++){
            System.out.print(i + " : " + lotList.get(i) + " | ");
        }
    }
    static void towAway(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Customer id: ");
        int customerID = sc.nextInt();
        
        Customer customer = Customer.custMap.get(customerID);
        if(customer == null){
            System.out.println("Invalid Customer ID! Add customer towAway");
            return;
        }
        
        System.out.println("Enter CarLot Id: ");
        int lotid = sc.nextInt();
        if(lotid >= 50 || lotid < 0){
            System.out.println("Invalid LOTNumber");
            return;
        }
        else{
            ParkingLot.totalCarLot+=1;
            ParkingLot.lotList.set(lotid,false);
            
        }
        Car car = Car.carMap.get(lotid);
        System.out.println("Enter End Time: ");
        double endTime = sc.nextDouble();
        ParkingLot parkLot = new ParkingLot(car, customer, 0.0, endTime);
        parkLot.generateBill();
    } 

    static boolean checkAvalability(int lot){
        if(ParkingLot.lotList.get(lot)==true){
            return false;
        }
        else{
            return true;
        }
    }

    void calculateCharge(){
        double timeDifference = endTime - car.startTime ;

        double gst = (5/100);
        double oneHourRate = 10.0;
        totalAmount = (oneHourRate * timeDifference) + gst;

    }
    void generateBill(){
        System.out.println("Invoice Generation");
        System.out.println("Customer Name: " + customer.name);
        System.out.println("Customer Mobile: " + customer.mobile);
        System.out.println("------------------------------------");
       
        System.out.println("Car Name: " + car.carName);
        calculateCharge();
        System.out.println("Total Charge: "+ totalAmount);
        System.out.println("------------------------------------");
    }

}
public class Main {
    static void display_Choices(){

        System.out.println("""
                1. Add Customer
                2. Add Car
                3. Display Customer
                4. Display CarLot
                5. Remove Car
                6. Exit
                """);
    }
    public static void main(String[] args) {
        System.out.println("SMMART PARKING lOT");

        while(true){
            display_Choices();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            
            if(choice == 1){
                Customer.addCustomer();
            }
            else if(choice == 2){
                Car.addCar();
            }
            else if(choice == 3){
                Customer.displayCustomer();
            }
            else if(choice == 4){
                Car.displayCar();
            }
            else if(choice == 5){
                Car.removeCar();
            }
            else if(choice == 6){
                System.out.println("Thank you! Visit Again!");
                break;
            }
            else{
                System.out.println("Invalid Choice!");
            }
        }
    }
}
