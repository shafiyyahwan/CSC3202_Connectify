import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Connectify extends Application {

	private HashMap<String, UserProfile> profiles = new HashMap<>();

	public void start(Stage stage) {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50, 50, 50, 50));

		// Application header
		Label label = new Label(" Welcome to Connectify ");
		label.setFont(Font.font("Lucida Console", FontPosture.ITALIC, 18));
		label.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;"
				+ " -fx-border-color: blue; -fx-border-width: 2px; " + "-fx-padding: 5px;");
		pane.setTop(label);
		BorderPane.setAlignment(label, Pos.CENTER);

		// Buttons for various functionalities
		Button bJoin = new Button("Join Network");
		bJoin.setPrefWidth(120);
		bJoin.setStyle("-fx-font-size: 13px");

		Button bLeave = new Button("Leave Network");
		bLeave.setPrefWidth(120);
		bLeave.setStyle("-fx-font-size: 13px");

		Button bCreate = new Button("Create Profile");
		bCreate.setPrefWidth(120);
		bCreate.setStyle("-fx-font-size: 13px");

		Button bModify = new Button("Modify Profile");
		bModify.setPrefWidth(120);
		bModify.setStyle("-fx-font-size: 13px");

		Button bSearch = new Button("Search Profiles");
		bSearch.setPrefWidth(120);
		bSearch.setStyle("-fx-font-size: 13px");

		Button bAddFriend = new Button("Add Friend");
		bAddFriend.setPrefWidth(120);
		bAddFriend.setStyle("-fx-font-size: 13px");

		// Join Network button action
		bJoin.setOnAction(e -> {
			Label lJoin = new Label("Enter username: ");
			TextField tfJoin = new TextField();
			tfJoin.setPromptText("e.g. CookieMonsterz12");
			tfJoin.setFocusTraversable(false);
			tfJoin.setMaxWidth(170);
			Button bConfirm = new Button("Confirm");
			Label lJoin1 = new Label();

			bConfirm.setOnAction(ev -> {
				String username = tfJoin.getText().trim().toLowerCase();
				if (profiles.containsKey(username)) {
					lJoin1.setText("Username is already exist.");
				} else if (username.isEmpty()) {
					lJoin1.setText("Please enter a username.");
				} else if (!username.matches("[a-zA-Z0-9]+")) {
					lJoin1.setText("Username cannot contain spaces or special \ncharacters.");
				} else {
					profiles.put(username, new UserProfile(username, "Unknown", "Unknown"));
					lJoin1.setText(username + " has joined the network.");
				}
			});

			GridPane pJoin = new GridPane();
			pJoin.add(lJoin, 0, 0);
			pJoin.add(tfJoin, 1, 0);
			pJoin.add(bConfirm, 1, 1);
			pJoin.add(lJoin1, 0, 2, 2, 1);

			pJoin.setHgap(10);
			pJoin.setVgap(10);
			pJoin.setPadding(new Insets(30));
			pJoin.setAlignment(Pos.TOP_CENTER);

			Stage sJoin = new Stage();
			sJoin.setTitle("Join Network");
			sJoin.setScene(new Scene(pJoin, 350, 170));
			sJoin.show();
		});

		// Leave Network button action
		bLeave.setOnAction(e -> {
			Label lLeave = new Label("Enter username: ");
			TextField tfLeave = new TextField();
			tfLeave.setPrefWidth(150);
			Button bConfirm = new Button("Confirm");
			Label lLeave1 = new Label();

			bConfirm.setOnAction(ev -> {
				String username = tfLeave.getText().trim().toLowerCase();
				if (profiles.containsKey(username)) {
					profiles.remove(username);
					lLeave1.setText(username + " has left the network.");
				} else {
					lLeave1.setText("Username does not exist.");
				}
			});

			GridPane pLeave = new GridPane();
			pLeave.add(lLeave, 0, 0);
			pLeave.add(tfLeave, 1, 0);
			pLeave.add(bConfirm, 1, 1);
			pLeave.add(lLeave1, 0, 2, 2, 1);

			pLeave.setHgap(10);
			pLeave.setVgap(10);
			pLeave.setPadding(new Insets(30));
			pLeave.setAlignment(Pos.TOP_CENTER);

			Stage sLeave = new Stage();
			sLeave.setTitle("Join Network");
			sLeave.setScene(new Scene(pLeave, 350, 170));
			sLeave.show();
		});

		// Create Profile button action
		bCreate.setOnAction(e -> {
			Label lUsername = new Label("Enter Username: ");
			TextField tfUsername = new TextField();
			tfUsername.setPrefWidth(180);

			Label lName = new Label("Enter Name:");
			TextField tfName = new TextField();

			Label lStatus = new Label("Status:");
			RadioButton rSingle = new RadioButton("Single");
			RadioButton rMarried = new RadioButton("Married");
			RadioButton rPreferNot = new RadioButton("Prefer not to say");

			Button bSelectImage = new Button("Select Image");
			ImageView profileImageView = new ImageView();
			profileImageView.setFitHeight(100);
			profileImageView.setFitWidth(100);

			Label lFeedback = new Label();

			ToggleGroup group = new ToggleGroup();
			rSingle.setToggleGroup(group);
			rMarried.setToggleGroup(group);
			rPreferNot.setToggleGroup(group);

			VBox vbox = new VBox(5, rSingle, rMarried, rPreferNot);

			Button bConfirm = new Button("Confirm");

			bSelectImage.setOnAction(ev -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters()
						.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
				File selectedFile = fileChooser.showOpenDialog(null);
				if (selectedFile != null) {
					Image image = new Image("file:" + selectedFile.getAbsolutePath());
					profileImageView.setImage(image);
				}
			});

			bConfirm.setOnAction(ev -> {
				String username = tfUsername.getText().trim().toLowerCase();
				String name = tfName.getText().trim().toLowerCase();
				String status = "";

				if (group.getSelectedToggle() == rSingle) {
					status = "Single";
				} else if (group.getSelectedToggle() == rMarried) {
					status = "Married";
				} else if (group.getSelectedToggle() == rPreferNot) {
					status = "-";
				}

				if (username.isEmpty() || name.isEmpty() || status.isEmpty()) {
					lFeedback.setText("All fields must be filled out!");
				} else if (!profiles.containsKey(username)) {
					lFeedback.setText("Username not found. Please create an account.");
				} else {
					// Create a new profile
					UserProfile newProfile = new UserProfile(username, name, status);
					if (profileImageView.getImage() != null) {
						newProfile.setImage(profileImageView.getImage());
					}
					profiles.put(username, newProfile);
					lFeedback.setText(username + "'s profile has been created.");
				}
			});

			GridPane pCreate = new GridPane();
			pCreate.add(lUsername, 0, 0);
			pCreate.add(tfUsername, 1, 0);
			pCreate.add(lName, 0, 1);
			pCreate.add(tfName, 1, 1);
			pCreate.add(lStatus, 0, 2);
			pCreate.add(vbox, 1, 2);
			pCreate.add(bSelectImage, 0, 3);
			pCreate.add(profileImageView, 1, 3);
			pCreate.add(bConfirm, 1, 4);
			pCreate.add(lFeedback, 0, 5, 2, 1);

			pCreate.setHgap(10);
			pCreate.setVgap(10);
			pCreate.setPadding(new Insets(15));
			pCreate.setAlignment(Pos.CENTER);

			Stage sCreate = new Stage();
			sCreate.setTitle("Create Profile");
			sCreate.setScene(new Scene(pCreate, 400, 400));
			sCreate.show();
		});

		// Modify Profile button action
		bModify.setOnAction(e -> {
			Button bmodifyUsername = new Button("Update Username");
			bmodifyUsername.setPrefWidth(120);

			Button bmodifyName = new Button("Update Name");
			bmodifyName.setPrefWidth(120);

			Button bmodifyStatus = new Button("Update Status");
			bmodifyStatus.setPrefWidth(120);

			Button bmodifyImage = new Button("Update Image");
			bmodifyImage.setPrefWidth(120);

			bmodifyUsername.setOnAction(ev -> {
				// Create labels and text fields for input
				Label lUsername = new Label("Enter Username: ");
				TextField tfUsername = new TextField();
				tfUsername.setPrefWidth(150);
				Label lNewUsername = new Label("Enter New Username: ");
				TextField tfNewUsername = new TextField();
				Label ModifyStatus = new Label();
				Button bUpdate = new Button("Update");

				// Create a GridPane layout for the form
				GridPane modifyGrid = new GridPane();
				modifyGrid.setVgap(10); // Vertical spacing between rows
				modifyGrid.setHgap(10); // Horizontal spacing between columns
				modifyGrid.setPadding(new Insets(20));

				// Add labels and text fields to the GridPane
				modifyGrid.add(lUsername, 0, 0); // Column 0, Row 0
				modifyGrid.add(tfUsername, 1, 0); // Column 1, Row 0
				modifyGrid.add(lNewUsername, 0, 1); // Column 0, Row 1
				modifyGrid.add(tfNewUsername, 1, 1); // Column 1, Row 1
				modifyGrid.add(bUpdate, 1, 2); // Button should be in row 2, column 1
				modifyGrid.add(ModifyStatus, 0, 3, 2, 1); // Status message in row 3, spanning 2 columns

				// Create a Stage to show the GridPane layout
				Stage modifyStage = new Stage();
				modifyStage.setTitle("Modify Username");
				modifyStage.setScene(new Scene(modifyGrid, 400, 200));
				modifyStage.show();

				// Handle the update action for bUpdate button
				bUpdate.setOnAction(evUpdate -> {
					// Retrieve the entered usernames
					String username = tfUsername.getText().trim().toLowerCase();
					String newUsername = tfNewUsername.getText().trim().toLowerCase();

					// Check if the original username exists
					if (username.isEmpty()) {
						ModifyStatus.setText("Please enter a username.");
					} else if (!profiles.containsKey(username)) {
						ModifyStatus.setText(username + " does not exist.");
					} else if (newUsername.isEmpty()) {
						ModifyStatus.setText("Please enter a new username.");
					} else if (newUsername.equals(username)) {
						ModifyStatus.setText("New username cannot be the same as the old username.");
					} else if (!newUsername.matches("[a-zA-Z0-9]+")) {
						ModifyStatus.setText("Username cannot contain spaces or special characters.");
					} else if (profiles.containsKey(newUsername)) {
						ModifyStatus.setText(newUsername + " already exists.");
					} else {
						// Update the user's profile with the new username
						UserProfile updatedProfile = profiles.get(username);
						updatedProfile.setUsername(newUsername);
						profiles.put(newUsername, updatedProfile); // Add new username with the updated profile
						profiles.remove(username); // Remove the old username from the map

						ModifyStatus.setText("Username has been updated to: " + newUsername);
					}
				});
			});

			bmodifyName.setOnAction(ev -> {
				// Create labels and text fields for the form
				Label lUsername = new Label("Enter Username: ");
				TextField tfUsername = new TextField();
				tfUsername.setPrefWidth(150); // Set the preferred width of the text field
				Label lName = new Label("Enter New Name: ");
				TextField tfName = new TextField();
				tfName.setPrefWidth(150); // Set the preferred width of the text field
				Label lFeedback = new Label(); // Label to show feedback messages
				Button bUpdate = new Button("Update");

				// Create a GridPane layout for the form
				GridPane modifyGrid = new GridPane();
				modifyGrid.setVgap(10); // Vertical spacing between rows
				modifyGrid.setHgap(10); // Horizontal spacing between columns
				modifyGrid.setPadding(new Insets(20));

				// Add labels, text fields, and button to the GridPane
				modifyGrid.add(lUsername, 0, 0); // Column 0, Row 0
				modifyGrid.add(tfUsername, 1, 0); // Column 1, Row 0
				modifyGrid.add(lName, 0, 1); // Column 0, Row 1
				modifyGrid.add(tfName, 1, 1); // Column 1, Row 1
				modifyGrid.add(bUpdate, 1, 2); // Column 1, Row 2
				modifyGrid.add(lFeedback, 0, 3, 2, 1); // Column 0, Row 3, span across 2 columns

				// Create a Stage to show the form
				Stage modifyStage = new Stage();
				modifyStage.setTitle("Modify Name");
				modifyStage.setScene(new Scene(modifyGrid, 400, 200));
				modifyStage.show();

				// Event handler for updating the name
				bUpdate.setOnAction(event -> {
					String username = tfUsername.getText().trim().toLowerCase();
					String newName = tfName.getText().trim();

					// Check if the username is empty or doesn't exist in profiles
					if (username.isEmpty()) {
						lFeedback.setText("Please enter a username.");
					} else if (!profiles.containsKey(username)) {
						lFeedback.setText(username + " does not exist.");
					} else if (newName.isEmpty()) {
						lFeedback.setText("Please enter a new name.");
					} else {
						// Retrieve the profile, update the name and put it back into the map
						UserProfile profile = profiles.get(username);
						profile.setName(newName);
						profiles.put(username, profile);

						// Provide feedback to the user
						lFeedback.setText("Name has been updated to: " + newName);
					}
				});
			});

			bmodifyStatus.setOnAction(ev -> {
				// Create labels, text fields, and buttons for input
				Label lUsername = new Label("Enter Username: ");
				TextField tfUsername = new TextField();
				tfUsername.setPrefWidth(150);

				Label lStatus = new Label("Status:");

				// Radio buttons for selecting status
				RadioButton rSingle = new RadioButton("Single");
				RadioButton rMarried = new RadioButton("Married");
				RadioButton rPreferNot = new RadioButton("Prefer not to say");

				Button bUpdate = new Button("Update");

				// Create a ToggleGroup for the radio buttons
				ToggleGroup group = new ToggleGroup();
				rSingle.setToggleGroup(group);
				rMarried.setToggleGroup(group);
				rPreferNot.setToggleGroup(group);

				// Create a GridPane to hold the components (for better alignment)
				GridPane modifyGrid = new GridPane();
				modifyGrid.setVgap(10); // Vertical spacing between rows
				modifyGrid.setHgap(10); // Horizontal spacing between columns
				modifyGrid.setPadding(new Insets(20));

				modifyGrid.add(lUsername, 0, 0);
				modifyGrid.add(tfUsername, 1, 0);
				modifyGrid.add(lStatus, 0, 1);
				modifyGrid.add(rSingle, 1, 1);
				modifyGrid.add(rMarried, 1, 2);
				modifyGrid.add(rPreferNot, 1, 3);
				modifyGrid.add(bUpdate, 1, 4);

				// Create a label to display feedback
				Label lFeedback = new Label();
				modifyGrid.add(lFeedback, 0, 5, 2, 1);

				// Create a Stage to show the form
				Stage modifyStage = new Stage();
				modifyStage.setTitle("Modify Status");
				modifyStage.setScene(new Scene(modifyGrid, 400, 250));
				modifyStage.show();

				// Handle the update action for status
				bUpdate.setOnAction(event -> {
					// Retrieve the entered username and selected status
					String username = tfUsername.getText().trim().toLowerCase();
					String status = "";

					if (username.isEmpty()) {
						lFeedback.setText("Please enter a valid username.");
						return;
					}

					// Check if the username exists in the profiles map
					if (!profiles.containsKey(username)) {
						lFeedback.setText(username + " does not exist.");
						return;
					}

					// Set the status based on the selected radio button
					if (group.getSelectedToggle() == rSingle) {
						status = "Single";
					} else if (group.getSelectedToggle() == rMarried) {
						status = "Married";
					} else if (group.getSelectedToggle() == rPreferNot) {
						status = "Prefer not to say";
					}

					// Update the user's status in the profiles map
					UserProfile profile = profiles.get(username);
					profile.setStatus(status); // Update status in the profile

					lFeedback.setText("Status updated to: " + status);

				});
			});

			bmodifyImage.setOnAction(ev -> {
				// Create labels, text fields, and buttons
				Label lUsername = new Label("Enter Username: ");
				TextField tfUsername = new TextField();
				tfUsername.setPrefWidth(150);

				Button bUpdate = new Button("Update");
				Button bSelectImage = new Button("Select Image");

				ImageView profileImageView = new ImageView();
				profileImageView.setFitHeight(100);
				profileImageView.setFitWidth(100);

				Label lFeedback = new Label();

				// Variable to hold the selected file
				final File[] selectedFile = { null };

				// Action for the "Select Image" button
				bSelectImage.setOnAction(event -> {
					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters()
							.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
					selectedFile[0] = fileChooser.showOpenDialog(null);
					if (selectedFile[0] != null) {
						Image image = new Image("file:" + selectedFile[0].getAbsolutePath());
						profileImageView.setImage(image);
					} else {
						lFeedback.setText("No image selected.");
					}
				});

				bUpdate.setOnAction(ev1 -> {
					String username = tfUsername.getText().trim().toLowerCase();
					if (username.isEmpty()) {
						lFeedback.setText("Please enter a username.");
					} else if (!profiles.containsKey(username)) {
						lFeedback.setText(username + " does not exist.");
					} else if (selectedFile[0] == null) {
						lFeedback.setText("No image selected. Please choose an image.");
					} else {
						// Update the profile with the new image
						Image image = new Image("file:" + selectedFile[0].getAbsolutePath());
						UserProfile profile = profiles.get(username);
						profile.setImage(image); // Update the image in the UserProfile
						profileImageView.setImage(image); // Reflect the update in the ImageView
						lFeedback.setText("Profile image updated successfully.");
					}
				});

				GridPane modifyImageGrid = new GridPane();
				modifyImageGrid.setHgap(10);
				modifyImageGrid.setVgap(10);
				modifyImageGrid.setPadding(new Insets(20));

				modifyImageGrid.add(lUsername, 0, 0);
				modifyImageGrid.add(tfUsername, 1, 0);
				modifyImageGrid.add(profileImageView, 1, 1);
				modifyImageGrid.add(bSelectImage, 0, 2);
				modifyImageGrid.add(bUpdate, 1, 2);
				modifyImageGrid.add(lFeedback, 0, 3, 2, 1);

				Stage modifyImageStage = new Stage();
				modifyImageStage.setTitle("Modify Profile Image");
				modifyImageStage.setScene(new Scene(modifyImageGrid, 400, 250));
				modifyImageStage.show();
			});

			VBox Mvbox = new VBox(10, bmodifyUsername, bmodifyName, bmodifyStatus, bmodifyImage);
			Mvbox.setAlignment(Pos.CENTER);

			Stage sModify = new Stage();
			sModify.setTitle("Modify Profile");
			sModify.setScene(new Scene(Mvbox, 300, 200));
			sModify.show();
		});

		// Search Profile button action
		bSearch.setOnAction(e -> {
			Label lSearch = new Label("Enter username: ");
			TextField tfSearch = new TextField();
			tfSearch.setPrefWidth(150);
			Button bSearchUser = new Button("Search");
			Label lSearchResult = new Label();
			ImageView profileImageView = new ImageView();
			profileImageView.setFitHeight(100);
			profileImageView.setFitWidth(100);

			bSearchUser.setOnAction(ev -> {
				String username = tfSearch.getText().trim().toLowerCase();
				if (profiles.containsKey(username)) {
					UserProfile profile = profiles.get(username);

					Label lProfileUsername = new Label("Username: " + profile.username);
					Label lProfileName = new Label("Name: " + profile.name);
					Label lProfileStatus = new Label("Status: " + profile.status);
					Label lFriends = new Label("Friends: " + profile.friends);
					VBox BProfile = new VBox(lProfileUsername, lProfileName, lProfileStatus, lFriends);
					if (profile.getImage() != null) {
						profileImageView.setImage(profile.getImage());
					} else {
						profileImageView.setImage(new Image("file:/C:/Users/User/Downloads/defaultProfile.png")); // default image

					}
					profileImageView.setFitWidth(100);
					profileImageView.setFitHeight(100);
					profileImageView.setStyle("-fx-border-color: black");

					Label lProfilePicture = new Label("Profile Picture: ");
					VBox vbox = new VBox(15, lProfilePicture, profileImageView, BProfile); 
																							
					vbox.setAlignment(Pos.CENTER);
					vbox.setPadding(new Insets(20));

					Scene profileScene = new Scene(vbox, 300, 270);
					Stage profileStage = new Stage();
					profileStage.setTitle("User Profile");
					profileStage.setScene(profileScene);
					profileStage.show();
				} else {
					lSearchResult.setText("Profile not found.");
				}

			});

			GridPane pSearch = new GridPane();
			pSearch.add(lSearch, 0, 0);
			pSearch.add(tfSearch, 1, 0);
			pSearch.add(bSearchUser, 1, 1);
			pSearch.add(lSearchResult, 1, 2);

			pSearch.setHgap(10);
			pSearch.setVgap(10);
			pSearch.setPadding(new Insets(30));
			pSearch.setAlignment(Pos.TOP_CENTER);

			Stage sSearch = new Stage();
			sSearch.setTitle("Search User");
			sSearch.setScene(new Scene(pSearch, 350, 170));
			sSearch.show();
		});

		// Add Friend button action
		bAddFriend.setOnAction(e -> {
			Label lAddFriend = new Label("Enter your username: ");
			TextField tfAddFriend = new TextField();
			Label lFrienduser = new Label("Enter friend's username: ");
			TextField tfFrienduser = new TextField();
			tfAddFriend.setPrefWidth(150);

			Button bAdd = new Button("Add Friend");
			Label lAddFriend1 = new Label();

			bAdd.setOnAction(ev -> {
				String userName = tfAddFriend.getText().trim().toLowerCase();
				String friendUserName = tfFrienduser.getText().trim().toLowerCase();

				if (userName.isEmpty() || friendUserName.isEmpty()) {
					lAddFriend1.setText("Please enter both your username and friend's username.");
				}
				else if (!profiles.containsKey(userName)) {
					lAddFriend1.setText("Username does not exist.");
				}
				else if (!profiles.containsKey(friendUserName)) {
					lAddFriend1.setText("Friend's username does not exist.");
				}
				else if (userName.equals(friendUserName)) {
					lAddFriend1.setText("You cannot add yourself as a friend.");
				} else {
					UserProfile userProfile = profiles.get(userName);
					userProfile.addFriend(friendUserName);

					UserProfile friendProfile = profiles.get(friendUserName);
					friendProfile.addFriend(userName);

					lAddFriend1.setText(userName + " has added " + friendUserName + " as a friend.");
				}
			});

			GridPane pAddFriend = new GridPane();
			pAddFriend.add(lAddFriend, 0, 0);
			pAddFriend.add(tfAddFriend, 1, 0);
			pAddFriend.add(lFrienduser, 0, 1);
			pAddFriend.add(tfFrienduser, 1, 1);
			pAddFriend.add(bAdd, 1, 2);
			pAddFriend.add(lAddFriend1, 0, 3, 2, 1);

			pAddFriend.setHgap(10);
			pAddFriend.setVgap(10);
			pAddFriend.setPadding(new Insets(15));
			pAddFriend.setAlignment(Pos.CENTER);

			Stage sLeave = new Stage();
			sLeave.setTitle("Add Friend");
			sLeave.setScene(new Scene(pAddFriend, 400, 250));
			sLeave.show();
		});

		VBox buttonBox = new VBox(10, bJoin, bLeave, bCreate, bModify, bSearch, bAddFriend);
		buttonBox.setPadding(new Insets(10, 10, 10, 10));
		buttonBox.setAlignment(Pos.CENTER);

		pane.setCenter(buttonBox);

		stage.setTitle("Connectify");
		stage.setScene(new Scene(pane, 400, 400));
		stage.show();
	}

	// Main method
	public static void main(String[] args) {
		launch(args);
	}
}

class UserProfile {
	String username;
	String name;
	String status;
	LinkedList<String> friends;
	private Image profileImage;

	public UserProfile(String username, String name, String status) {
		this.username = username;
		this.name = name;
		this.status = status;
		this.friends = new LinkedList<>();
		this.profileImage = null;
	}

	public String getUsername() {  // Getter for username
		return username;
	}

	public void setUsername(String username) { // Setter for name
		this.username = username;

	}

	public String getName() {  // Getter for name
		return name;
	}

	public void setName(String name) {  // Setter for name
		this.name = name;
	}

	public String getStatus() {  // Getter for status
		return status;
	}

	public void setStatus(String status) {  // Setter for status
		this.status = status;
	}

	public Image getImage() {  // Getter for image
		return profileImage;
	}

	public void setImage(Image image) {  // Setter for image
		this.profileImage = image;
	}

	public void addFriend(String friendUsername) {  // Add friend
		if (!friends.contains(friendUsername)) {
			friends.add(friendUsername);
		}
	}

	public LinkedList<String> getFriends() {  // Getter friend
		return this.friends;
	}

	public String toString() {  // Return string representation
		return "Username: " + username + ", Name: " + name + ", Status: " + status + ", Friends: " + friends;
	}
}