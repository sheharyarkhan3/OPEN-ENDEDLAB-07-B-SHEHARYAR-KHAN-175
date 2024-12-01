package KarachiRealEstateAppLAB07SHEHARYARKHAN175TASK5;
		import java.util.LinkedList;
		import java.util.Queue;
		// Shared buffer for properties
		class PropertyBuffer {
		    private final Queue<Property> properties = new LinkedList<>();
		    private final int capacity;

		    public PropertyBuffer(int capacity) {
		        this.capacity = capacity;
		    }
		    // Synchronized method for the producer to add properties
		    public synchronized void addProperty(Property property) throws InterruptedException {
		        while (properties.size() == capacity) {
		            wait(); // Wait until there is space in the buffer
		        }
		        properties.add(property);
		        System.out.println("OFFERED:" + property.getAddress());
		        notify(); // Notify the consumer that a property has been added
		    }

		    // Synchronized method for the consumer to remove properties
		    public synchronized Property removeProperty() throws InterruptedException {
		        while (properties.isEmpty()) {
		            wait(); // Wait until there is a property to consume
		        }
		        Property property = properties.poll();
		        System.out.println("OWNED:" + property.getAddress());
		        notify(); // Notify the producer that space is available
		        return property;
		    }
		}
		// Producer thread that adds properties
		class OFFERED extends Thread {
		    private final PropertyBuffer buffer;

		    public OFFERED(PropertyBuffer buffer) {
		        this.buffer = buffer;
		    }

		    @Override
		    public void run() {
		        String[] addresses = {
		            "B-16 Main University Road, Karachi",
		            "B-13 Gulshan Road, Karachi",
		            "B11/A north nazimabad Road, Karachi"
		        };

		        try {
		            for (String address : addresses) {
		                Property property = new Property(String.valueOf(address.hashCode()), address, Math.random() * 10000000);
		                buffer.addProperty(property);
		                Thread.sleep(1000); // Simulate time taken to add a property
		            }
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		        }
		    }
		}

		// Consumer thread that removes properties
		class OWNED extends Thread {
		    private final PropertyBuffer buffer;

		    public OWNED(PropertyBuffer buffer) {
		        this.buffer = buffer;
		    }
		    @Override
		    public void run() {
		        try {
		            for (int i = 0; i < 3; i++) {
		                Property property = buffer.removeProperty();
		                Thread.sleep(1500); // Simulate time taken to process a property
		            }
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		        }
		    }
		}
		// Class representing a property
		class Property {
		    private String id;
		    private String address;
		    private double price;

		    public Property(String id, String address, double price) {
		        this.id = id;
		        this.address = address;
		        this.price = price;
		    }

		    public String getId() {
		        return id;
		    }

		    public String getAddress() {
		        return address;
		    }
		    public double getPrice() {
		        return price;
		    }
		}
		// Main class to demonstrate inter-thread communication
		public class KarachiRealEstateAppLAB07SHEHARYARKHAN175TASK5 {
			public static void main(String[] args) {
		        PropertyBuffer buffer = new PropertyBuffer(2); // Set buffer capacity
		        OFFERED offered = new OFFERED (buffer);
		        OWNED owned  = new OWNED (buffer);
		        offered .start();
		        owned.start();
		        try {
		            offered .join();
		            owned .join();
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		        }
		        System.out.println("LAB#07 TASK:05 SHEHARYAR KHAN!!!!");
		        System.out.println("ALL TASK COMPLETE SUCCESSFULLY !!!!");
		// TODO Auto-generated method stub
	}
}