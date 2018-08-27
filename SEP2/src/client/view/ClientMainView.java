package client.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.ClientMain;
import client.view.admin.addAccount.AddAccount;
import client.view.admin.assignAccToCus.AccountToCustomer;
import client.view.admin.menu.MenuNavigation;
import client.view.admin.newUser.CreateNewUser;
import client.view.admin.search.Search;
import client.view.customer.transfer.TransferMoney;
import client.view.customer.contacts.Contacts;
import client.view.customer.home.CustomerHome;
import client.view.customer.home.transactions.TransactionsList;
import client.view.customer.menu.MenuController;
import client.view.customer.settings.Settings;
import client.view.login.Login;
import client.view.login.nemId.NemId;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.Account;
import shared.Customer;

public class ClientMainView extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ClientMain clientController;
    private AnchorPane addAccount;
    private AnchorPane createNewUser;
    private AnchorPane login;
    private AnchorPane banner;
    private AnchorPane assignAccToCus;
    private AnchorPane transferMoney;
    private AnchorPane customerHome;
    private AnchorPane contacts;
    private AnchorPane settings;
    private AnchorPane search;

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        launch(args);
    }

	public void start(Stage primaryStage) throws Exception {


        ClientMain client = new ClientMain();
        clientController = client;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ONLINE BANKING SYSTEM");

        this.primaryStage.getIcons().add(new Image("file:resources/images/ContentImageHandler.png"));

        initRootLayout();
        showBanner();
//      showAddAccount();

        showLogin();

        //showNemIdDialog(123);
//        showTransferMoney();
//        showMenuNavigation();
//        showCreateNewUser();

//        showCustomerHome();


        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(1);
            }
        });
	}

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientMainView.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showLogin(){
    	try{
       	// Load  login page.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientMainView.class.getResource("login/Logind.fxml"));
        this.login = (AnchorPane) loader.load();

        // Give the create login controller class access to the client main class and this class.
        Login loginController = loader.getController();
        loginController.setViewController(this);
        loginController.setClientController(clientController);

        // Set login page into the center of root layout.
        rootLayout.setCenter(login);
        rootLayout.setLeft(null);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }



    /**
     * Shows the menu overview insid e the root layout.
     */
    public void showMenuNavigation() {
    	try {
    	// Load  menu navigation.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientMainView.class.getResource("admin/menu/MenuNavigation.fxml"));
        AnchorPane menuNavigation = (AnchorPane) loader.load();

        // Set menu navigation into the left of root layout.
        rootLayout.setLeft(menuNavigation);

        // Give the create new user controller access to the client main class and this class.
        MenuNavigation menuController = loader.getController();
        menuController.setViewController(this);
        menuController.setClientController(clientController);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Shows the create new user page inside the root layout.
     */
    public void showCreateNewUser(){
    	try {
    		if(createNewUser == null){
        	// Load  create new user page.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientMainView.class.getResource("admin/newUser/CreateNewUser.fxml"));
            this.createNewUser = (AnchorPane) loader.load();

            // Give the create new user controller access to the client main class and this class.
            CreateNewUser createNewUserController = loader.getController();
            createNewUserController.setViewController(this);
            createNewUserController.setClientController(clientController);
    		}
            // Set create new user page into the center of root layout.
            rootLayout.setCenter(createNewUser);

        	} catch (IOException e) {
        		e.printStackTrace();
        	}
    }

    public void showAddAccount(){
    	try {
    		if(addAccount==null){
        	// Load  menu navigation.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientMainView.class.getResource("admin/addAccount/AdAccount.fxml"));
            this.addAccount = (AnchorPane) loader.load();

            AddAccount addAccount = loader.getController();
            addAccount.setViewController(this);
            addAccount.setClientController(clientController);
    		}
            // Set menu navigation into the left of root layout.
            rootLayout.setCenter(addAccount);

        	} catch (IOException e) {
        		e.printStackTrace();
        	}
    }

    public String showNemIdDialog(String key){
    	try {
    	// Load  nem id stage.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientMainView.class.getResource("login/nemId/Nemid.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Nem-ID");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        NemId controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setViewController(this);
        controller.setClientController(clientController);
        controller.setKey(key);

        dialogStage.showAndWait();

        return controller.getValue();

    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public void showBanner(){

    	try {
    		if(banner == null){
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(ClientMainView.class.getResource("customer/banner/Banner.fxml"));
         banner = (AnchorPane) loader.load();
    	}
         rootLayout.setTop(banner);

    	 } catch (IOException e) {
     		e.printStackTrace();
    	 }
   }


    public void showCustomerMenu(){
   	 try {
   		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientMainView.class.getResource("customer/menu/SideBareMenu.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        rootLayout.setLeft(page);

        //Give the create new user controller access to the client main class and this class.
         MenuController menuController = loader.getController();
         menuController.setViewController(this);
         menuController.setClientController(clientController);

   	 	} catch (IOException e) {
    		e.printStackTrace();
   	 	}
    }

    public void showTransferMoney(){

      	 try {
      		TransferMoney controller = null;
      		if(transferMoney == null){
      		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientMainView.class.getResource("customer/transfer/PaymentsTransf.fxml"));
            transferMoney = (AnchorPane) loader.load();

            //Give the create new user controller access to the client main class and this class.
             controller = loader.getController();
             controller.setViewController(this);
             controller.setClientController(clientController);
             controller.setCustomer(clientController.getCustomerData());
      	 }
      		 rootLayout.setCenter(transferMoney);
      	 } catch (IOException e) {
       		e.printStackTrace();
      	 }
    }

	public void showAssignAccToCus() {
		 try {
			 if(assignAccToCus == null){
	      	   FXMLLoader loader = new FXMLLoader();
	           loader.setLocation(ClientMainView.class.getResource("admin/assignAccToCus/AssignAccToCus.fxml"));
	           assignAccToCus = (AnchorPane) loader.load();

	           //Give the create new user controller access to the client main class and this class.
	           AccountToCustomer controller = loader.getController();
	           controller.setViewController(this);
	           controller.setClientController(clientController);
		 	}
	           rootLayout.setCenter(assignAccToCus);

	      	 } catch (IOException e) {
	       		e.printStackTrace();
	      	 }
	}

	public void showCustomerHome() {
		 try {

	      	   FXMLLoader loader = new FXMLLoader();
	           loader.setLocation(ClientMainView.class.getResource("customer/home/CustomerHome.fxml"));
	           customerHome = (AnchorPane) loader.load();

	           //Give the create new user controller access to the client main class and this class.
	           CustomerHome controller = loader.getController();
	           controller.setViewController(this);
	           controller.setClientController(clientController);
	           controller.setCustomer();
	           controller.setListView();

	           rootLayout.setCenter(customerHome);

	      	 } catch (IOException e) {
	       		e.printStackTrace();
	      	 }
	}

	public void showCustomerHome(Customer customer) {
		 try {
	      	   FXMLLoader loader = new FXMLLoader();
	           loader.setLocation(ClientMainView.class.getResource("customer/home/CustomerHome.fxml"));
	           customerHome = (AnchorPane) loader.load();

	           //Give the create new user controller access to the client main class and this class.
	           CustomerHome controller = loader.getController();
	           controller.setViewController(this);
	           controller.setClientController(clientController);
	           controller.setCustomer(customer);
	           controller.setListView();

	           rootLayout.setCenter(customerHome);

	      	 } catch (IOException e) {
	       		e.printStackTrace();
	      	 }
	}

	public void showTransactions(Account account) {
		 try {
		   		FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(ClientMainView.class.getResource("customer/home/transactions/TransactionsList.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();

		        //Give the create new user controller access to the client main class and this class.
		         TransactionsList controller = loader.getController();
		         controller.setAccount(account);
		         controller.setTransactions();

		         rootLayout.setCenter(page);

		   	 	} catch (IOException e) {
		    		e.printStackTrace();
		   	 	}
	}

	public void showContacts() {
		try {
		if(contacts == null){
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClientMainView.class.getResource("customer/contacts/Contact.fxml"));
			contacts = (AnchorPane) loader.load();

			//Give the create new user controller access to the client main class and this class.

			Contacts contactController = loader.getController();
			contactController.setViewController(this);
			contactController.setClientController(clientController);
			contactController.setCustomer();
		}
			rootLayout.setCenter(contacts);


		} catch (IOException e) {
    		e.printStackTrace();
   	 	}
	}

	public void showSettings() {
		try{
			if(settings == null){
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(ClientMainView.class.getResource("customer/settings/ChangeInfo.fxml"));
				settings = (AnchorPane) loader.load();

				//Give the create new user controller access to the client main class and this class.
				Settings settingsController = loader.getController();
				settingsController.setViewController(this);
				settingsController.setClientController(clientController);
				settingsController.setCustomer();
			}
				rootLayout.setCenter(settings);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void showSearch(){
		try{
			if(search==null){
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(ClientMainView.class.getResource("admin/search/Search.fxml"));
				search = (AnchorPane) loader.load();

				//Give the create new user controller access to the client main class and this class.
				Search searchController = loader.getController();
				searchController.setViewController(this);
				searchController.setClientController(clientController);
			}
			rootLayout.setCenter(search);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void customerLogout(){
	    login = null;
	    transferMoney = null;
	    customerHome = null;
	    contacts = null;
	    settings = null;
	}

	public void adminLogout(){
		 addAccount = null;
		 createNewUser = null;
		 login = null;
		 assignAccToCus = null;
	}

}