package edu.uta.cs.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

public class FileSystemProcessor {

	static Map<String, FileSystem> allDisks = new HashMap<>();
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		start();
	}

	public static void start() {
		System.out.println("Select from the following available operations");
		System.out.println("----------------------------------------------------");
		System.out.println("1. Create specified number of filesystems");
		System.out.println("2. Create a filesystem with specified name");
		System.out.println("3. Write a txt file into a filesystem");
		System.out.println("4. Write a csv file into  filesystem");
		System.out.println("5. Format a specified filesystem");
		System.out.println("6. Read the files from a specified filesystem");
		System.out.println("7. List all the files from a specified filesystem");
		System.out.println("8. Copy external file to specified filesystem");
		System.out.println("9. Copy file in specified filesystem to OS");
		System.out.println("10. Delete file from a specified filesystem");
		System.out.println("11. Create link between files and test them");
		System.out.println("12. Set Owner for the files in filesystem");
		System.out.println("100. EXIT");

		int selectedOption = s.nextInt();

		switch (selectedOption) {
		case 1:
			beginCreateFileSystems();
			s.reset();
			start();
			break;
		case 2:
			beginCustomFileSystemCreation();
			s.reset();
			start();
			break;
		case 3:
			beginWritingTextFileInSpecifiedFilesystem();
			s.reset();
			start();
			break;
		case 4:
			beginWritingCsvFileInSpecifiedFilesystem();
			s.reset();
			start();
			break;
		case 5:
			beginFormattingSpecifiedFilesystem();
			s.reset();
			start();
			break;
		case 6:
			beginReadingFromSpecifiedFilesystem();
			s.reset();
			start();
			break;
		case 7:
			beginListingFilesOfSpecifiedFilesystem();
			s.reset();
			start();
			break;
		case 8:
			beginCopyingFilesFromOSToSpecifiedFilesystem();
			s.reset();
			start();
			break;
		case 9:
			beginCopyingFilesFromSpecifiedFilesystemToOS();
			s.reset();
			start();
			break;
		case 10:
			beginDeletingFilesFromFilesystem();
			s.reset();
			start();
			break;
		case 11:
			beginLinkingFilesInFilesystem();
			s.reset();
			start();
			break;
		case 12:
			beginSettingOwnerForFilesinFilesystem();
			s.reset();
			start();
			break;
		case 100:
			s.close();
			break;
		}

	}

	// creating number of mentioned disks
	public static void beginCreateFileSystems() {

		System.out.println("Enter the number of disks to be created : ");
		int numberOfDisks = s.nextInt();
		createFilesystems(numberOfDisks);
		System.out.println("Number of Disks created : " + allDisks.size());
	}

	// creating a filesystem with the specified name
	public static void beginCustomFileSystemCreation() {

		System.out.println("Enter the name of disk to be created : ");
		createCustomFilesystem(s.next());
		System.out.println("Number of Disks created : " + allDisks.size());
	}

	// write to text file to specified filesystem
	public static void beginWritingTextFileInSpecifiedFilesystem() {

		String textFilesystem = "";
		try {
			System.out.println("Enter the name of disk (text file) to be written into : ");
			textFilesystem = s.next();
			writeToTextFileInFilesystem(allDisks.get(textFilesystem));
		} catch (IOException e) {
			System.out.println(
					"Path not found  for writing into text file: " + allDisks.get(textFilesystem).getPath("/data"));
			e.printStackTrace();
		}
	}

	// write csv file to specified filesystem
	public static void beginWritingCsvFileInSpecifiedFilesystem() {

		String csvFilesystem = "";
		try {
			System.out.println("Enter the name of disk (csv file) to be written into : ");
			csvFilesystem = s.next();
			writeToCsvFileInFilesystem(allDisks.get(csvFilesystem));
		} catch (IOException e) {
			System.out.println(
					"Path not found  for writing nto csv file: " + allDisks.get(csvFilesystem).getPath("/data"));
			e.printStackTrace();
		}
	}

	// read all files in the specified filesystem
	public static void beginReadingFromSpecifiedFilesystem() {

		String toReadFilesystem = "";
		try {
			System.out.println("Enter the name of disk to be read from : ");
			toReadFilesystem = s.next();
			readAllFilesInFilesystem(allDisks.get(toReadFilesystem));
		} catch (IOException e) {
			System.out.println(
					"Path not found  while reading from text file: " + allDisks.get(toReadFilesystem).getPath("/data"));
			e.printStackTrace();
		}
	}

	// format all files in the specified filesystem
	public static void beginFormattingSpecifiedFilesystem() {

		String toFormatFilesystem = "";
		try {
			System.out.println("Enter the name of disk to be formatted : ");
			toFormatFilesystem = s.next();
			formatFilesystem(allDisks.get(toFormatFilesystem));
		} catch (IOException e) {
			System.out.println("Path not found  while formatting filesystem: "
					+ allDisks.get(toFormatFilesystem).getPath("/data"));
			e.printStackTrace();
		}
	}

	// list all files in the specified filesystem
	public static void beginListingFilesOfSpecifiedFilesystem() {

		String specifiedFilesystem = "";
		try {
			System.out.println("Enter the name of disk to view the list of all files : ");
			specifiedFilesystem = s.next();
			listFilesOfSpecifiedFilesystem(allDisks.get(specifiedFilesystem));
		} catch (IOException e) {
			System.out.println("Path not found  while formatting filesystem: "
					+ allDisks.get(specifiedFilesystem).getPath("/data"));
			e.printStackTrace();
		}
	}

	// copy file from OS to specified filesystem
	public static void beginCopyingFilesFromOSToSpecifiedFilesystem() {

		
		String specifiedFilesystem = "";
		String fileName = "";
		String InPath = "";
		try {
			System.out.println("Enter the name of disk to copy the file from OS : ");
			specifiedFilesystem = s.next();
			System.out.println("Enter the file path in OS you want to copy : ");
			Scanner n = new Scanner(System.in);
			InPath = n.nextLine();
			System.out.println("Enter the name of file in filesystem you want to copy to OS : ");
			Scanner f = new Scanner(System.in);
			fileName = f.nextLine();
			copyFileFromOSToFilesystem(Paths.get(InPath), allDisks.get(specifiedFilesystem),fileName);
		} catch (IOException e) {
			System.out.println("Path not found  while formatting filesystem: "
					+ allDisks.get(specifiedFilesystem).getPath("/data"));
			e.printStackTrace();
		}
	}
	
	// copy file from specified filesystem to OS
	public static void beginCopyingFilesFromSpecifiedFilesystemToOS() {
		
		String specifiedFilesystem = "";
		String fileName = "";
		String OutPath = "";
		try {
			System.out.println("Enter the name of disk to copy the file from disk to OS: ");
			specifiedFilesystem = s.next();
			System.out.println("Enter the name of file in filesystem you want to copy to OS : ");
			Scanner n = new Scanner(System.in);
			fileName = n.nextLine();
			System.out.println("Enter the destination in OS where the file has to be copied");
			Scanner o = new Scanner(System.in);
	        OutPath = o.nextLine();
			copyFileFromFilesystemToOS(allDisks.get(specifiedFilesystem),
					Paths.get(OutPath),fileName);
		} catch(IOException e) {
			System.out.println("Path not found  while formatting filesystem: "
					+ allDisks.get(specifiedFilesystem).getPath("/data"));
			e.printStackTrace();
		}
			
		
	}
	
	//delete files from disk system
	public static void beginDeletingFilesFromFilesystem() {
		
		String specifiedFilesystem = "";
		String fileName = "";
		try {
			
		System.out.println("Enter the name of disk to delete files: ");
		specifiedFilesystem = s.next();
		System.out.println("Enter the name of file in filesystem you want to delete : ");
		Scanner n = new Scanner(System.in);
		fileName = n.nextLine();
		Files.deleteIfExists(allDisks.get(specifiedFilesystem).getPath("/data").resolve(fileName));
		System.out.println("File has been successfully deleted");
		}
		catch (IOException e) {
			System.out.println("No such file exist in this disk system");
			e.printStackTrace();
		}
	}
	
	// Linking files in file system 
	public static void beginLinkingFilesInFilesystem() {
		
		String specifiedFilesystem = "";
		try {
		System.out.println("Enter the name of disk to get files linked: ");
		specifiedFilesystem = s.next();
		createLinkBetweenFiles(allDisks.get(specifiedFilesystem));
		}
		catch(IOException e) {
			System.out.println("No linking possible");
			e.printStackTrace();
		}
		
	}
	
	//Setting or Chaging owner for files in filesystem
	public static void beginSettingOwnerForFilesinFilesystem() {
		
		String specifiedFilesystem = "";
		String fileName = "";
		try {
			System.out.println("Enter the name of disk to set owner for files in it: ");
			specifiedFilesystem = s.next();
			System.out.println("Enter the name of file in filesystem you want to set ownership for : ");
			Scanner n = new Scanner(System.in);
			fileName = n.nextLine();
			settingOwnershipForFiles(allDisks.get(specifiedFilesystem),fileName);
		}
		catch(IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}
		
			
	
	/**
	 * Create the mentioned number of disks and add them to the static list of filesystems
	 * 
	 * 
	 * @param numberOfDisks
	 */
	public static void createFilesystems(int numberOfDisks) {

		for (int i = 1; i <= numberOfDisks; i++) {
			FileSystem fs = Jimfs.newFileSystem("disk0" + i,
					Configuration.unix().toBuilder().setBlockSize(256).build());
			Path data = fs.getPath("/data");
			try {
				Files.createDirectory(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			allDisks.put("disk0" + i, fs);
		}
	}

	/**
	 * Creating a custom filesystem with the name given by user and adding it to the
	 * static list of Filesystems
	 * 
	 * @param nameOfTheFileSystem
	 */
	public static void createCustomFilesystem(String nameOfTheFileSystem) {

		FileSystem fs = Jimfs.newFileSystem(nameOfTheFileSystem,
				Configuration.unix().toBuilder().setBlockSize(256).build());
		Path data = fs.getPath("/data");
		try {
			Files.createDirectory(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		allDisks.put(nameOfTheFileSystem, fs);

	}

	/**
	 * Formating a filesystem from the root level
	 * 
	 * @throws IOException
	 */
	public static void formatFilesystem(FileSystem fs) throws IOException {
		// Files.walk(fs.getPath("/data")).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
	}

	/**
	 * Writing into a text file in a filesystem - disk01
	 * 
	 * @param fs
	 * @throws IOException
	 */
	public static void writeToTextFileInFilesystem(FileSystem fs) throws IOException {
		Path testFilePath = fs.getPath("/data").resolve("test.txt"); // /data/test.txt
		Files.write(testFilePath, ImmutableList.of("hello world"), StandardCharsets.UTF_8);
	}

	/**
	 * Writing into a csv file in a filesystem - disk01
	 * 
	 * @param fs
	 * @throws IOException
	 */
	public static void writeToCsvFileInFilesystem(FileSystem fs) throws IOException {
		Path testFilePath = fs.getPath("/data").resolve("data.csv"); // /data/data.csv
		Files.write(testFilePath, ImmutableList.of("test1,test2\ntest3,test4"), StandardCharsets.UTF_8);
	}

	/**
	 * Reading all the files in the filesystem - disk01
	 * 
	 * @param fs
	 * @throws IOException
	 */
	public static void readAllFilesInFilesystem(FileSystem fs) throws IOException {
		Files.list(fs.getPath("/data")).forEach(file -> {
			try {
				System.out.println(String.format("Path of file : %s, size of file - (%db)", file,
						Files.readAllBytes(file).length));
				BufferedReader br = Files.newBufferedReader(file);
				String st;
				while ((st = br.readLine()) != null)
					System.out.println(st);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * List files of a specified filesystem
	 * 
	 * @param fs
	 * @throws IOException
	 */
	public static void listFilesOfSpecifiedFilesystem(FileSystem fs) throws IOException {

		Files.list(fs.getPath("/data")).forEach(file -> {
			try {
				System.out.println(String.format("Path of file : %s, size of file - (%db)", file,
						Files.readAllBytes(file).length));
			} catch (IOException e) {
				System.out.println("No files exist in this disk system");
				e.printStackTrace();
			}
		});
	}

	/**
	 * Copies files from specified location to the filesystem
	 * 
	 * @param externalFilePath
	 * @param fs
	 * @param fileName
	 * @throws IOException
	 */
	public static void copyFileFromOSToFilesystem(Path externalFilePath, FileSystem fs,String fileName) throws IOException {
		Path filePaste = fs.getPath("/data").resolve(fileName);
		String ext = externalFilePath + "/" + fileName;
		Path external = Paths.get(ext);
		Path copiedFile = Files.copy(external, filePaste, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("file copied is:" + copiedFile);
	}

	/**
	 * Copies files from specified location to the filesystem
	 * 
	 * @param externalFilePath
	 * @param fs
	 * @param fileName
	 * @throws IOException
	 */
	/* Copies out files from file system to OS */
	public static void copyFileFromFilesystemToOS(FileSystem fs,Path externalFilePath,String fileName) throws IOException {
	    Path fileCopy = fs.getPath("/data").resolve(fileName);
		String ext = externalFilePath + "/" + fileName;
		Path external = Paths.get(ext);
	    Path copiedFile = Files.copy(fileCopy, external, StandardCopyOption.REPLACE_EXISTING);
	    System.out.println("file successfully copied into OS");
	}
	
	/**
	 * Creation of Link Between Files
	 * 
	 * @param fs
	 * @throws IOException
	 */
	public static void createLinkBetweenFiles(FileSystem fs) throws IOException{
		
		Path test_path = fs.getPath("/data");
		Path original_file = test_path.resolve("test-file.txt");
		Files.write(original_file,"Test Data".getBytes());
	    
		//create link
		Path link_file = test_path.resolve("linked-test-file.txt");
		Path link = Files.createLink(link_file,original_file);
		System.out.println("File link created: "+link);
		
		//reading from link
		String linkData = new String(Files.readAllBytes(link));
		System.out.println("Content of Linked File: "+linkData);
		
		//Testing link- modifying original file
		Files.write(original_file,"...Modified data...".getBytes(),StandardOpenOption.APPEND);
		String newlink_data = new String(Files.readAllBytes(link));
		System.out.println("Content of Linked File after modifying original file: "+newlink_data);
	}
	
	//Setting ownership
	public static void settingOwnershipForFiles(FileSystem fs,String fileName) throws IOException{
		
		Path path = fs.getPath("/data").resolve(fileName);
		System.out.println("path is: "+path);
		FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
		UserPrincipal owner = ownerAttributeView.getOwner();
        System.out.println("owner: " + owner.getName());
     }
}
