import java.util.*;

class BookingManager {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, String> reservations = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public BookingManager() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
        reservations.put("Single-1", "Abhisheak");
    }

    public void cancelBooking(String reservationId) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation failed. Reservation not found.");
            return;
        }

        String roomType = reservationId.split("-")[0];

        reservations.remove(reservationId);
        rollbackStack.push(reservationId);

        inventory.put(roomType, inventory.get(roomType) + 1);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        System.out.println();
        System.out.println("Rollback History (Most Recent First):");

        while (!rollbackStack.isEmpty()) {
            System.out.println("Released Reservation ID: " + rollbackStack.pop());
        }

        System.out.println();
        System.out.println("Updated " + roomType + " Room Availability: " + inventory.get(roomType));
    }
}

public class BookApp {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        System.out.println("Booking Cancellation");
        manager.cancelBooking("Single-1");
    }
}