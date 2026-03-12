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

    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    private Set<String> allocatedRooms = new HashSet<>();

    private Map<String, Set<String>> roomTypeMap = new HashMap<>();

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
