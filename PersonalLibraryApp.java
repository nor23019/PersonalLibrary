import java.io.*;
import java.util.*;

public class PersonalLibraryApp {

    private static final String FILE_NAME = "library.csv";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. View library");
            System.out.println("2. Borrowing a book");
            System.out.println("3. Returning a book");
            System.out.println("4. Search a book");
            System.out.println("5. Exit");
            System.out.print("Enter a number: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    System.out.print("Enter the book to borrow: ");
                    String borrowB = scanner.nextLine();
                    borrowBook(borrowB);
                    System.out.println("");
                    break;
                case 3:
                    System.out.print("Enter the book to return: ");
                    String returnB = scanner.nextLine();
                    returnBook(returnB);
                    System.out.println("");
                    break;
                case 4:
                    System.out.print("Enter the book to search: ");
                    String searchB = scanner.nextLine();
                    searchBooks(searchB);
                    System.out.println("");
                    break;
                case 5:
                    System.out.println("See you soon!");
                    return;
                default:
                    System.out.println("Please select an option between 1 and 5.");
            }
        }
    }

    private static void viewBooks() throws IOException {
        List<String[]> library = readBooks();
        if (library.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println("");
        System.out.printf("%-20s %-30s %-10s\n", "Title", "Author", "Status");
        for (String[] book : library) {
            System.out.printf("%-20s %-30s %-10s\n", book[0], book[1], book[2]);
        }
    }

    private static void borrowBook(String title) throws IOException {
        List<String[]> library = readBooks();
        boolean found = false;

        for (String[] book : library) {
            if (book[0].equalsIgnoreCase(title) && book[2].equals("Available")) {
                book[2] = "Borrowed";
                found = true;
                System.out.println("The book was borrowed!");
                break;
            }
        }

        if (!found) {
            System.out.println("The book has already been borrowed.");
        }

        writeBooks(library);
    }

    private static void returnBook(String title) throws IOException {
        List<String[]> library = readBooks();
        boolean found = false;

        for (String[] book : library) {
            if (book[0].equalsIgnoreCase(title) && book[2].equals("Borrowed")) {
                book[2] = "Available";
                found = true;
                System.out.println("Book returned successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found or it wasn't borrowed.");
        }

        writeBooks(library);
    }

    private static void searchBooks(String query) throws IOException {
        List<String[]> library = readBooks();
        boolean found = false;

        System.out.println("");
        System.out.printf("%-20s %-30s %-10s\n", "Title", "Author", "Status");
        for (String[] book : library) {
            if (book[0].toLowerCase().contains(query.toLowerCase())) {
                System.out.printf("%-20s %-30s %-10s\n", book[0], book[1], book[2]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found matching the search query.");
        }
    }

    private static List<String[]> readBooks() throws IOException {
        List<String[]> library = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return library;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                library.add(line.split(","));
            }
        }

        return library;
    }

    private static void writeBooks(List<String[]> library) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String[] book : library) {
                bw.write(String.join(",", book));
                bw.newLine();
            }
        }
    }
}
