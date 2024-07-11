import java.util.*;

public class ResortBooking {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Resort obj1 = new Resort();
        int roomBooked = 0;

        String[][] details = {
                {"101", "Luxury", "2000", "Available", "dd/mm/yyyy-dd/mm/yyyy"},
                {"102", "Luxury", "2000", "Booked", "01/01/2023-05/01/2023"},
                {"103", "Luxury", "2000", "Available", "dd/mm/yyyy-dd/mm/yyyy"},
                {"104", "Luxury", "2000", "Booked", "01/01/2023-05/01/2023"},
                {"105", "Luxury", "2000", "Available", "dd/mm/yyyy-dd/mm/yyyy"},
                {"106", "Luxury", "2000", "Booked", "01/01/2023-05/01/2023"},
                {"107", "Suite", "1500", "Available", "dd/mm/yyyy-dd/mm/yyyy"},
                {"108", "Suite", "1500", "Booked", "01/01/2023-05/01/2023"},
                {"109", "Suite", "1500", "Available", "dd/mm/yyyy-dd/mm/yyyy"},
                {"110", "Suite", "1500", "Booked", "01/01/2023-05/01/2023"},
                {"111", "Suite", "1500", "Available", "dd/mm/yyyy-dd/mm/yyyy"},
                {"112", "Suite", "1500", "Booked", "01/01/2023-05/01/2023"}
        };

        for (String[] i : details) {
            obj1.addRoom(new Room(Integer.parseInt(i[0]), i[1], Integer.parseInt(i[2]), i[3], i[4]));
        }

        boolean condition = true;

        while (condition) {
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("1.addRoom \n2.showRoomDetails \n3.Booking \n4.delete room \n5.Calculate price \n6.cancel booking \n7.exit");
            int n = sc.nextInt();
            System.out.println("-----------------------------------------------------------------------------------------------------------");

            switch (n) {
                case 1: {
                    if (obj1.admin(sc)) {
                        System.out.print("enter room no:");
                        int roomNo = sc.nextInt();
                        sc.nextLine();
                        System.out.print("enter room type:");
                        String roomType = sc.nextLine();
                        System.out.print("enter price:");
                        int price = sc.nextInt();
                        sc.nextLine();
                        System.out.print("availability status:");
                        String avail = sc.nextLine();
                        System.out.print("enter date:");
                        String date = sc.nextLine();
                        obj1.addRoom(new Room(roomNo, roomType, price, avail, date));
                    } else {
                        System.out.println("Incorrect password");
                    }
                    break;
                }
                case 2: {
                    obj1.showRoom();
                    break;
                }
                case 3: {
                    sc.nextLine();
                    System.out.println("\t\t\t\t\t\t____RESORT WELCOMES YOU___\t\t\t\t");
                    System.out.println("\t\t\t\t\t\tSTART YOUR BOOKING\t\t\t\t");
                    System.out.print("enter room type Luxury or Suite:");
                    String room = sc.nextLine();
                    System.out.print("enter days: ");
                    int days = sc.nextInt();
                    System.out.print("enter no of people:");
                    int peo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("enter dates of stay (dd/mm/yyyy-dd/mm/yyyy):");
                    String date = sc.nextLine();
                    System.out.println("enter preferred room no 101-106 luxury and 107-112 suite rooms");
                    int roomNo = sc.nextInt();
                    roomBooked = roomNo;
                    obj1.book(room, days, peo, date, roomNo, sc);
                    break;
                }
                case 4: {
                    if (obj1.admin(sc)) {
                        System.out.println("enter room no to be deleted");
                        int x = sc.nextInt();
                        obj1.delRoom(x);
                    } else {
                        System.out.println("Incorrect password");
                    }
                    break;
                }
                case 5: {
                    sc.nextLine();
                    System.out.print("enter room type:");
                    String room = sc.nextLine();
                    System.out.print("enter no of days:");
                    int days = sc.nextInt();
                    obj1.calcPrice(room, days);
                    break;
                }
                case 6: {
                    sc.nextLine();
                    System.out.println("\t\t\t\t\t\tCANCEL BOOKING\t\t\t\t");
                    System.out.println("room no to be cancelled booking:" + roomBooked);
                    obj1.cancel(roomBooked);
                    break;
                }
                case 7: {
                    condition = false;
                    sc.close();
                    break;
                }
                default: {
                    System.out.println("invalid choice");
                }
            }
        }
    }
}

// creating room
class Room {
    int roomNo;
    String roomType;
    int price;
    String availability;
    String date;

    Room(int roomNo, String roomType, int price, String availability, String date) {
        this.roomType = roomType;
        this.roomNo = roomNo;
        this.price = price;
        this.availability = availability;
        this.date = date;
    }
}

// storing room
class Resort {
    Scanner sc = new Scanner(System.in);
    int f = 0;
    public HashMap<Integer, Room> items = new HashMap<>();

    void showRoom() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("| RoomNo | Room Type | Price | Availability | Date         |");
        System.out.println("------------------------------------------------------------");
        List<Integer> sortedRoomNumbers = new ArrayList<>(items.keySet());
        Collections.sort(sortedRoomNumbers);
        for (Integer i : sortedRoomNumbers) {
            Room x = items.get(i);
            System.out.printf("| %-7d| %-10s| %-6d| %-13s| %-13s|\n", x.roomNo, x.roomType, x.price, x.availability, x.date);
        }
    }

    void addRoom(Room obj) {
        if (items.containsKey(obj.roomNo)) {
            System.out.println("Room no already exist");
        } else {
            items.put(obj.roomNo, obj);
        }
    }

    void delRoom(int roomNo) {
        if (items.containsKey(roomNo)) {
            items.remove(roomNo);
        } else {
            System.out.println("Room no. does not exist");
        }
    }

    void cancel(int roomNo) {
        if (f == 1) {
            Room g = items.get(roomNo);
            g.availability = "Available";
            items.put(roomNo, g);
            System.out.println("Room is cancelled successfully");
        } else {
            System.out.println("you have not booked any rooms yet");
        }
    }

    void roomConfirm(int roomNo, String date) {
        Room x = items.get(roomNo);
        if (x != null) {
            x.availability = "Booked";
            if (x.date.equals("dd/mm/yyyy-dd/mm/yyyy")) {
                x.date = date;
            } else {
                x.date += "," + date;
            }
            items.put(roomNo, x);
        }
    }

    void desiredRoomAvail(String room) {
        for (Integer i : items.keySet()) {
            Room x = items.get(i);
            if (x.roomType.equals(room)) {
                System.out.println(x.roomNo + "                    " + x.roomType + "                   " + x.price + "             " + x.availability + "     " + x.date);
            }
        }
    }

    boolean admin(Scanner sc) {
        String adminN = "Admin123";
        String pass = "Admin";
        sc.nextLine();
        System.out.println("enter admin username");
        String enterAdmin = sc.nextLine();
        System.out.println("Enter admin password:");
        String enterpass = sc.nextLine();
        return adminN.equals(enterAdmin) && pass.equals(enterpass);
    }

    void book(String room, int days, int peo, String date, int roomNo, Scanner sc) {
        if (available(roomNo, date)) {
            System.out.println("room is free!! you can proceed");
            calcPrice(room, days);
            System.out.println("1.user details and payment\n2.quit");
            int choose = sc.nextInt();

            if (choose == 1) {
                f = 1;
                sc.nextLine(); 

                System.out.println("Enter your name:");
                String username = sc.nextLine();

                String phone;
                while (true) {
                    System.out.println("Enter your phone number:");
                    phone = sc.nextLine();
                    if (phone.matches("\\d{10}")) {
                        break;
                    } else {
                        System.out.println("Invalid phone number. Please enter a valid 10-digit phone number.");
                    }
                }

                String email;
                while (true) {
                    System.out.println("Enter your email:");
                    email = sc.nextLine();
                    if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                        break;
                    } else {
                        System.out.println("Invalid email address. Please enter a valid email.");
                    }
                }
            }

            if (f == 1) {
                Room x = items.get(roomNo);
                roomConfirm(roomNo, date);
                System.out.println("room booked successfully");
            }
        } else {
            System.out.println("oops!! room is already booked\n enter another room");
            System.out.println("availability status of " + room + " are:");
            desiredRoomAvail(room);
        }
    }

    boolean available(int roomNo, String date) {
        Room x = items.get(roomNo);
        if (x != null) 
        {
            String[] bookingPeriods = x.date.split(",");
            for (String period : bookingPeriods) 
            {
                if (!period.trim().equals("dd/mm/yyyy-dd/mm/yyyy")) 
                {
                    String[] dates = period.split("-");
                    String startDate = dates[0];
                    String endDate = dates[1];
                    if (isDateInRange(date, startDate, endDate)) 
                    {
                        return false; // Date is within the booked range
                    }
                }
            }
            return true; 
        } else {
            System.out.println("Room does not exist");
            return false;
        }
    }

    boolean isDateInRange(String date, String startDate, String endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    void calcPrice(String room, int days) {
        int o;
        if (room.equals("Luxury")) {
            System.out.println("cost per night for luxury room is 2000");
            System.out.println("your total days of stay: " + days);
            o = 2000 * days;
            System.out.println("grand total:" + o);
        } else {
            System.out.println("cost per night for suite room is 1500");
            System.out.println("your total days of stay: " + days);
            o = 1500 * days;
            System.out.println("grand total:" + o);
        }
    }
}
