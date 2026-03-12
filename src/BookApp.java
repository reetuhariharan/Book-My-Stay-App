import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class BookApp {

    // Queue for FIFO booking processing
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Set to ensure unique room IDs
    private Set<String> allocatedRooms = new HashSet<>();

    // Map room type -> allocated room IDs
    private Map<String, Set<String>> roomTypeMap = new HashMap<>();

    // Track room numbers per type
    private Map<String, Integer> roomCounter = new HashMap<>();

    public void addBooking(String guest, String roomType) {
        bookingQueue.add(new BookingRequest(guest, roomType));
    }

    private String generateRoomId(String roomType) {

        int count = roomCounter.getOrDefault(roomType, 0) + 1;
        roomCounter.put(roomType, count);

        String roomId = roomType + "-" + count;

        allocatedRooms.add(roomId);

        roomTypeMap
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        return roomId;
    }

    public void processBookings() {

        System.out.println("Room Allocation Processing");

        while (!bookingQueue.isEmpty()) {

            BookingRequest request = bookingQueue.poll();

            String roomId = generateRoomId(request.roomType);

            System.out.println(
                    "Booking confirmed for Guest: " +
                            request.guestName +
                            ", Room ID: " +
                            roomId
            );
        }
    }

    public static void main(String[] args) {

        BookApp service = new BookApp();

        service.addBooking("Abhi", "Single");
        service.addBooking("Subha", "Single");
        service.addBooking("Vanmathi", "Suite");

        service.processBookings();
    }
}