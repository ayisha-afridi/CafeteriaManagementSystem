import org.example.Admin;
import org.example.User;
import org.example.foodList;
import org.example.foodNode;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class CafeteriaManagementTest {

    private foodList menu;
    private foodList requests;
    private Admin admin;
    private User user;

    @BeforeEach
    void setUp() {
        // initialize instances before each test
        menu = new foodList();
        requests = new foodList();
        admin = new Admin();
        user = new User();

        // clear files before each test
        menu.clearMenuFile();
        requests.clearReqFile();
        menu.clearCompFile();
    }

    @AfterEach
    void tearDown() {
        // clean up files after each test
        menu.clearMenuFile();
        requests.clearReqFile();
        menu.clearCompFile();
    }

    @Test
    @DisplayName("Test foodNode default constructor")
    void testFoodNodeDefaultConstructor() {
        foodNode node = new foodNode();
        assertEquals("None", node.name);
        assertEquals("None", node.origin);
        assertEquals("None", node.allergen);
        assertEquals(0, node.calories);
        assertEquals(0.0, node.price);
        assertNull(node.link);
    }

    @Test
    @DisplayName("Test foodNode full constructor")
    void testFoodNodeFullConstructor() {
        foodNode node = new foodNode("Pizza", "Italy", "Gluten", 300, 15.5);
        assertEquals("Pizza", node.name);
        assertEquals("Italy", node.origin);
        assertEquals("Gluten", node.allergen);
        assertEquals(300, node.calories);
        assertEquals(15.5, node.price);
        assertNull(node.link);
    }

    @Test
    @DisplayName("Test foodNode request constructor")
    void testFoodNodeRequestConstructor() {
        foodNode node = new foodNode("Sushi", "Japan");
        assertEquals("Sushi", node.name);
        assertEquals("Japan", node.origin);
        assertNull(node.link);
    }

    @Test
    @DisplayName("Test adding first node to empty list")
    void testAddTailToEmptyList() {
        foodNode node = menu.addTail("Burger", "USA", "None", 500, 10.0);

        assertNotNull(node);
        assertNotNull(menu.front);
        assertEquals("Burger", menu.front.name);
        assertEquals(1, menu.length());
    }

    @Test
    @DisplayName("Test adding multiple nodes")
    void testAddMultipleNodes() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);
        menu.addTail("Tacos", "Mexico", "None", 250, 8.5);

        assertEquals(3, menu.length());
        assertEquals("Pizza", menu.front.name);
    }

    @Test
    @DisplayName("Test adding request node")
    void testAddRequestNode() {
        foodNode node = requests.addTail("Ramen", "Japan");

        assertNotNull(node);
        assertEquals("Ramen", node.name);
        assertEquals("Japan", node.origin);
        assertEquals(1, requests.length());
    }

    @Test
    @DisplayName("Test delete from empty list")
    void testDeleteFromEmptyList() {
        foodNode deleted = menu.deleteAtPosition(1);
        assertNull(deleted);
    }

    @Test
    @DisplayName("Test delete at invalid position")
    void testDeleteAtInvalidPosition() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);

        assertNull(menu.deleteAtPosition(0));
        assertNull(menu.deleteAtPosition(5));
        assertNull(menu.deleteAtPosition(-1));
    }

    @Test
    @DisplayName("Test delete first node")
    void testDeleteFirstNode() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);

        menu.deleteAtPosition(1);

        assertEquals(1, menu.length());
        assertEquals("Sushi", menu.front.name);
    }

    @Test
    @DisplayName("Test delete middle node")
    void testDeleteMiddleNode() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);
        menu.addTail("Tacos", "Mexico", "None", 250, 8.5);

        menu.deleteAtPosition(2);

        assertEquals(2, menu.length());
        assertEquals("Pizza", menu.front.name);
    }

    @Test
    @DisplayName("Test delete last node")
    void testDeleteLastNode() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);

        menu.deleteAtPosition(2);

        assertEquals(1, menu.length());
        assertEquals("Pizza", menu.front.name);
    }

    @Test
    @DisplayName("Test length of empty list")
    void testLengthEmptyList() {
        assertEquals(0, menu.length());
    }

    @Test
    @DisplayName("Test length after adding nodes")
    void testLengthAfterAdding() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        assertEquals(1, menu.length());

        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);
        assertEquals(2, menu.length());

        menu.addTail("Tacos", "Mexico", "None", 250, 8.5);
        assertEquals(3, menu.length());
    }

    @Test
    @DisplayName("Test length after deleting nodes")
    void testLengthAfterDeleting() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);
        menu.addTail("Tacos", "Mexico", "None", 250, 8.5);

        assertEquals(3, menu.length());

        menu.deleteAtPosition(2);
        assertEquals(2, menu.length());

        menu.deleteAtPosition(1);
        assertEquals(1, menu.length());
    }

    @Test
    @DisplayName("Test check availability - item exists")
    void testCheckAvailabilityItemExists() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        menu.checkAvailability("Pizza");

        assertTrue(outContent.toString().contains("Pizza is currently on the menu!"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test check availability - item does not exist")
    void testCheckAvailabilityItemNotExists() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        menu.checkAvailability("Burger");

        assertTrue(outContent.toString().contains("Burger is not currently on the menu."));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test check availability - case insensitive")
    void testCheckAvailabilityCaseInsensitive() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        menu.checkAvailability("pizza");

        assertTrue(outContent.toString().contains("Pizza is currently on the menu!"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test approve single request")
    void testApproveSingleRequest() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        requests.addTail("Ramen", "Japan");

        admin.approveRequest(menu, requests, 1, "Soy", 400, 12.5);

        assertEquals(2, menu.length());
        assertEquals(0, requests.length());
    }

    @Test
    @DisplayName("Test approve multiple requests")
    void testApproveMultipleRequests() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        requests.addTail("Ramen", "Japan");
        requests.addTail("Tacos", "Mexico");

        admin.approveRequest(menu, requests, 1, "Soy", 400, 12.5);

        assertEquals(2, menu.length());
        assertEquals(1, requests.length());
        assertEquals("Tacos", requests.front.name);
    }

    @Test
    @DisplayName("Test approve request at position 2")
    void testApproveRequestAtPosition2() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        requests.addTail("Ramen", "Japan");
        requests.addTail("Tacos", "Mexico");
        requests.addTail("Burger", "USA");

        admin.approveRequest(menu, requests, 2, "None", 350, 10.0);

        assertEquals(2, menu.length());
        assertEquals(2, requests.length());
    }

    @Test
    @DisplayName("Test get food at valid index")
    void testGetFoodAtValidIndex() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);
        menu.addTail("Tacos", "Mexico", "None", 250, 8.5);

        foodNode food = user.getFoodAtIndex(menu, 2);

        assertNotNull(food);
        assertEquals("Sushi", food.name);
    }

    @Test
    @DisplayName("Test get food at invalid index")
    void testGetFoodAtInvalidIndex() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);

        foodNode food = user.getFoodAtIndex(menu, 5);

        assertNull(food);
    }

    @Test
    @DisplayName("Test get food at index 1")
    void testGetFoodAtFirstIndex() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.addTail("Sushi", "Japan", "Fish", 200, 20.0);

        foodNode food = user.getFoodAtIndex(menu, 1);

        assertNotNull(food);
        assertEquals("Pizza", food.name);
    }

    @Test
    @DisplayName("Test add complaint to file")
    void testAddComplaintToFile() throws IOException {
        user.addCompFile("Food was cold");

        BufferedReader reader = new BufferedReader(new FileReader("Complaint.txt"));
        String line = reader.readLine();

        assertEquals("Food was cold", line);
        reader.close();
    }

    @Test
    @DisplayName("Test menu file is created after adding item")
    void testMenuFileCreated() throws IOException {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);

        File menuFile = new File("Menu.txt");
        assertTrue(menuFile.exists());

        BufferedReader reader = new BufferedReader(new FileReader(menuFile));
        String line = reader.readLine();

        assertTrue(line.contains("Pizza"));
        assertTrue(line.contains("Italy"));
        reader.close();
    }

    @Test
    @DisplayName("Test request file is created after adding request")
    void testRequestFileCreated() throws IOException {
        requests.addTail("Ramen", "Japan");

        File requestFile = new File("Request.txt");
        assertTrue(requestFile.exists());

        BufferedReader reader = new BufferedReader(new FileReader(requestFile));
        String line = reader.readLine();

        assertTrue(line.contains("Ramen"));
        assertTrue(line.contains("Japan"));
        reader.close();
    }

    @Test
    @DisplayName("Test clear menu file")
    void testClearMenuFile() throws IOException {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.clearMenuFile();

        BufferedReader reader = new BufferedReader(new FileReader("Menu.txt"));
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    @DisplayName("Test delete only node in list")
    void testDeleteOnlyNode() {
        menu.addTail("Pizza", "Italy", "Gluten", 300, 15.0);
        menu.deleteAtPosition(1);

        assertEquals(0, menu.length());
        assertNull(menu.front);
    }

    @Test
    @DisplayName("Test operations on empty list")
    void testOperationsOnEmptyList() {
        assertEquals(0, menu.length());
        assertNull(menu.deleteAtPosition(1));
        assertNull(user.getFoodAtIndex(menu, 1));
    }

    @Test
    @DisplayName("Test adding nodes with special characters")
    void testAddNodeWithSpecialCharacters() {
        menu.addTail("Pad Thai (Spicy!)", "Thailand", "Peanuts, Soy", 350, 14.99);

        assertEquals(1, menu.length());
        assertEquals("Pad Thai (Spicy!)", menu.front.name);
    }

    @Test
    @DisplayName("Test adding nodes with zero values")
    void testAddNodeWithZeroValues() {
        menu.addTail("Water", "Generic", "None", 0, 0.0);

        assertEquals(1, menu.length());
        assertEquals(0, menu.front.calories);
        assertEquals(0.0, menu.front.price);
    }
}