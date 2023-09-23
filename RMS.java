
import java.util.Scanner;

class Order {
    int[] items = new int[10];
    int[] qty = new int[10];
    private int totalOrderAmount;
    public Order()
    {
        totalOrderAmount = 0;
        FoodMenu.getFoodMenu();
    }
    void setOrder(int ptr, int val, int qty)
    {
        items[ptr] = val;
        this.qty[ptr] = qty;
        totalOrderAmount += (qty * FoodMenu.prices[val]);
    }
    public int getTotalOrderPrice()
    {
        return totalOrderAmount;
    }
}

class Customer {
    Scanner sc =new Scanner (System.in );
    static int count= 100;
    private int timeSlot;
    private int tableNo;
    int id;
    private String name;
    private Long mobNo;
    Order orders;
    int numberOfOrders = 0;
    
    public Customer(String name,int timeSlot, int tableNo, Long mobNo)
    {
        count ++;
        id = count;
        this.name = name;
        this.timeSlot = timeSlot;
        this.tableNo = tableNo;
        this.mobNo = mobNo;
    }
    public void setOrders()
    {
        orders = new Order();
        do {
            System.out.print("Enter the item number: ");
            int n =sc.nextInt();
            System.out.print("Enter Quantity: ");
            int q =sc.nextInt();
            orders.setOrder(numberOfOrders,n-1, q);
            numberOfOrders++;
            System.out.print("0(exit) or 1(continue): ");
            int flag= sc.nextInt();
            if(flag==0) {
                
                break;
            }
                  
        }while(true);
        
    }
    
    public void getCustomerDetails()
    {
        System.out.println("Customer Details\n------------------------------------------\n");
        System.out.println("Name: " + name);
        System.out.println("Id: " + id);
        System.out.println("Mobile Number: " + mobNo);
        System.out.println("TimeSlot: " + MyRestaurant.getTime(timeSlot));
        System.out.println("Table Booked: " + tableNo);

    }
    public int getSpendAmount() 
    {
        return orders.getTotalOrderPrice();
    }
}

class Table {
    int tableNumber;
    Customer cust;
    boolean occupied;
    public Table(int n) {
        tableNumber = n;
        occupied = false;

    }
}

class FoodMenu {
    static String[] itemNames = {
        "CHILLI CHICKEN GRAVY (8P)",
        "CHICKEN MANCHURIAN GRAVY (8P)",
        "CHICKEN IN HOT GARLIC SAUCE",
        "LEMON CORIANDER CHICKEN GRAVY",
        "SCHEZWAN CHICKEN GRAVY",
        "CHICKEN IN HUNAN SAUCE",
        "CHICKEN IN OYSTER SAUCE",
        "FISH MANCHURIAN GRAVY",
        "FISH CHILLI GRAVY",
        "LEMON CORIANDER FISH GRAVY" };
    static int[] prices = {159, 159, 159,169, 169, 179, 179, 219, 219, 219};
    public static void getFoodMenu()
    {
        System.out.println("Non Vegetarian Main course\n-------------------------------\n");
        for(int i = 0; i<10; i++)
        {
            System.out.printf("%-3d %-40s %-5d Rs\n",i+1, itemNames[i], prices[i]);
        }
    }
}

class MyRestaurant {
    static int[] customerList = new int[10];
    static Customer[] customerDetailsList = new Customer[10];
    private int totalCustomer=0;
    Scanner sc = new Scanner(System.in);
    private static int totalCollection = 0;
    static int highestBill = 0;
    Customer lucky;
    static String timeSlots[] ={
        "11:00 - 12:00",
        "12:00 - 13:00",
        "13:00 - 14:00",
        "14:00 - 15:00",
        "15:00 - 16:00",        
        "16:00 - 17:00",        
        "17:00 - 18:00",        
        "18:00 - 19:00",        
        "19:00 - 20:00",        
        "20:00 - 21:00",        
        "21:00 - 22:00",        
        "22:00 - 23:00",        

    };
    int[] noOfUnoccupiedTables = {0,0,0,0,0,0,0,0,0,0,0,0};
    Table[][] tables = new Table[12][7];

    public MyRestaurant() {
        for(int i = 0; i<12; i++)
        {
            for(int j = 0; j<7; j++)
            {
                tables[i][j] = new Table(j+1);
            }
        }
    }
    static void displayTimeSlots()
    {
        System.out.println("All Time Slots\n----------------------------------\n");
        for(String s: timeSlots)
        {
            System.out.println(s);
        }
    }
    static String getTime(int n)
    {
        return timeSlots[n-1];
    }
    public void displayUnoccupiedTable(int timeSlot)
    {
        for(int i =0; i<7; i++)
        {
            if(tables[timeSlot-1][i].occupied == false)
            {
                System.out.println("Table "+(i+1));
            }
        }
    }
    public void printBill()
    {
        boolean flag = false;

        System.out.println("Enter Customer Id: ");
        int i = sc.nextInt();
        System.out.printf("%25s%20s%25s\n"," ", "Zaika Restaurant", " ");
        System.out.println("------------------------------------------------------------");
       System.out.printf("%30s%10s%30s\n"," ", "Raniganj", " ");
        System.out.println("------------------------------------------------------------");

        for(int r = 0; r<totalCustomer; r++)
        {
            if(i==customerList[r])
            {
                flag = true;
                Customer c = customerDetailsList[r];
                c.getCustomerDetails();

                System.out.println("------------------------------------------------------------");
                System.out.printf("%-5s %-30s %-15s %-15s\n","Qty","Food Name", "Price",
                 "Total Price");
                for(int j =0; j<c.numberOfOrders;j++)
                {
                    int itemNo = c.orders.items[j];
                    int itemQty = c.orders.qty[j];
                    
                    System.out.printf("%-5d %-30s %-15d %-15d \n",itemQty, FoodMenu.itemNames[itemNo],
                     FoodMenu.prices[itemNo], FoodMenu.prices[itemNo]*itemQty);
                }
                System.out.println("------------------------------------------------------------");
                System.out.printf("%15s%10s%15s%10d\n"," ","Total - ", " ",c.orders.getTotalOrderPrice());
                System.out.println("------------------------------------------------------------");
                System.out.printf("%15s%20s%15s"," ","Thank you visit again", " ");
                break;
            }
        }
        if(!flag){System.out.println("Customer not found!!");}
    }
    public int displayOccupiedTable(int timeSlot)
    {
        int flag = 0;
        for(int i =0; i<7; i++)
        {
            if(tables[timeSlot-1][i].occupied == true)
            {
                System.out.println("Table "+(i+1));
                flag = 1;
            }
        }
        return flag;
    }
    public boolean IsTableAvailable(int timeSlot)
    {
        int count = 0;
        for(int i =0; i<7; i++)
        {
            if(tables[timeSlot-1][i].occupied == false)
            {
               count ++;
            }
        }
        if(count==0) {
            return false;
        } else {
            return true;
        }
        
    }
    public void bookTable()
    {
        
        System.out.println("Choose Your time Slot: ");
        displayTimeSlots();
        int ts = sc.nextInt();
        if(IsTableAvailable(ts))
        {
            System.out.println("\nAvailable Tables: \n");
            displayUnoccupiedTable(ts);
            System.out.println("Choose Your table: ");
            int tn = sc.nextInt();
            tables[ts-1][tn-1].occupied = true;
            // create customer
            String name;
            Long mobNo;
            System.out.print("Enter your name: ");
            sc.nextLine();
            name = sc.nextLine();
            System.out.print("Enter your Contact Number: ");
            mobNo = sc.nextLong();

            tables[ts-1][tn-1].cust = new Customer(name, ts, tn, mobNo);
            tables[ts-1][tn-1].cust.setOrders();
            int bill = tables[ts-1][tn-1].cust.getSpendAmount();
            customerList[totalCustomer] = tables[ts-1][tn-1].cust.id;
            customerDetailsList[totalCustomer] = tables[ts-1][tn-1].cust;
            totalCustomer++;
            totalCollection += bill;
            if(highestBill < bill)
            {
                highestBill = bill;
                lucky = tables[ts-1][tn-1].cust;
            }
            System.out.println("\nSuccessfully Booked\nCustomer Id- " + tables[ts-1][tn-1].cust.id);

        }   else {
                System.out.println("Sorry, We are Booked!!");
        }
            
    }

    public void getTableDetails()
    {
        System.out.println("Choose Your time Slot: ");
        displayTimeSlots();
        System.out.print(">>>");
        int ts = sc.nextInt();
        System.out.println("\nBooked Table in this slot:");
        System.out.println("------------------------------------------------------------------------------");
        int temp = displayOccupiedTable(ts);
        if(temp == 0)
        {
            System.out.println("\nNo booking found");
            return;
        }
        
        System.out.println("Choose Your table: ");
        System.out.print(">>>");
        int tn = sc.nextInt();
        tables[ts-1][tn-1].cust.getCustomerDetails();
    }
    public void getFoodMenu()
    {
        FoodMenu.getFoodMenu();
    }
    public void getTotalCollection()
    {
        System.out.println("Total Collection of the day: " + totalCollection);
    }
    public void getLuckyCustomer()
    {
        lucky.getCustomerDetails();
    }

    public void getAllCustomers()
    {
        System.out.println("\nCustomer Count = " + totalCustomer);
        for(int i = 0; i< 12; i++)
        {
            for(int j = 0; j< 7; j++)
            {
                if(tables[i][j].occupied == true)
                {
                    tables[i][j].cust.getCustomerDetails();
                    System.out.println("\n");
                }
            }
        }
    }
    public void getHighestBill()
    {
        System.out.println("\nHighest bill  of the day - " + highestBill +"Rs");
    }
    public void checkTableAvailability()
    {
        for(int i = 0; i<12; i++)
        {
                for(int j = -1; j<7; j++)
                {
                    if(j<0)
                    {
                        System.out.printf("%-17s ",timeSlots[i]);
                        continue;
                    }
                    if(tables[i][j].occupied == true)
                    {
                        System.out.printf("%-5d ",tables[i][j].cust.id );
                    }
                    else
                    {
                        System.out.printf("%-5d ",0 );
                    }

                }
            System.out.println();
        }
    }

}

public class RMS {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        MyRestaurant zaika = new MyRestaurant();
        boolean open = true;
        do {
            System.out.printf("%15s%25s%15s\n"," ","Zaika Restaurant"," ");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("1. Food Menu");
            System.out.println("2. Check Table Availability");
            System.out.println("3. Book a Table");
            System.out.println("4. Total Amount collected till now");
            System.out.println("5. Booked Table Details");
            System.out.println("6. All Customers till now");
            System.out.println("7. Highest Bill of the day");
            System.out.println("8. Lucky Customer of the day");
            System.out.println("9. Print Bill");
            System.out.print(">>>");
            int choice = sc.nextInt();
            switch(choice)
            {
                case 1:
                    zaika.getFoodMenu();
                    break;
                case 2:
                    zaika.checkTableAvailability();
                    break;
                case 3:
                    zaika.bookTable();
                    break;
                case 4:
                    zaika.getTotalCollection();
                    break;
                case 5:
                    zaika.getTableDetails();
                    break;
                case 6:
                    zaika.getAllCustomers();
                    break;
                case 7:
                    zaika.getHighestBill();
                    break;
                case 8:
                    zaika.getLuckyCustomer();
                    break;
                case 9:
                    zaika.printBill();
                    break;
                default:
                    System.out.println("Invalid Choice");

            }
            System.out.print("\nIs restaurant still open(0-No, 1-Yes): ");
            choice = sc.nextInt();
            if(choice == 0)
            open = false;
        }while(open);
        System.out.println("\nRestaurant Closed!!");
        sc.close();
        

    }
}