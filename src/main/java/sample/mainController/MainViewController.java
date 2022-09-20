package sample.mainController;

import javafx.animation.Animation; //Graphical User Interface import needed for the animation of clock
import javafx.animation.KeyFrame; //Graphical User Interface import needed for the animation of clock
import javafx.animation.Timeline; //Graphical User Interface import needed for the animation of clock
import javafx.event.EventHandler; //Graphical User Interface import needed to change colors of buttons when entered
import javafx.fxml.FXML; //Graphical User Interface import needed to create connection to FXML files
import javafx.fxml.Initializable; //Graphical User Interface import needed to create initializable method
import javafx.scene.control.Button; //Graphical User Interface import needed to operate with buttons
import javafx.scene.control.Label; //Graphical User Interface import needed to operate with labels
import javafx.scene.input.MouseEvent; //Graphical User Interface import needed to detect mouse's events
import javafx.scene.paint.Color; //Graphical User Interface import to use colors
import javafx.util.Duration; //Graphical User Interface import needed for the animation of clock
import sample.*;

import java.io.IOException; //needed for exception when button clicked
import java.net.URL; //needed for the initializable method
import java.time.LocalDateTime; //needed for using current data about date and hour
import java.time.ZonedDateTime; //needed for using current time with milliseconds
import java.util.ArrayList; //needed for using lists
import java.util.Collections; //needed for using lists
import java.util.List; //needed for using lists
import java.util.ResourceBundle; //needed for the initializable method
import java.util.concurrent.TimeUnit; //needed to stop the application for a second


public class MainViewController implements Initializable {

    private Main main;

    @FXML
    private Label HourLabel;

    @FXML
    private Label MinuteLabel;

    @FXML
    private Label SecondLabel;

    @FXML
    private Button BUTTON;

    public Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
        //algorithm for setting hour number
        if (LocalDateTime.now().getHour() < 10) {
            HourLabel.setText("0" + LocalDateTime.now().getHour());
        } else {
            HourLabel.setText(String.valueOf(LocalDateTime.now().getHour()));
        }

        //algorithm for setting minute number
        if (LocalDateTime.now().getMinute() < 10) {
            MinuteLabel.setText("0" + LocalDateTime.now().getMinute());
        } else {
            MinuteLabel.setText(String.valueOf(LocalDateTime.now().getMinute()));
        }

        //algorithm for setting second number
        if (LocalDateTime.now().getSecond() < 10) {
            SecondLabel.setText("0" + LocalDateTime.now().getSecond());
        } else {
            SecondLabel.setText(String.valueOf(LocalDateTime.now().getSecond()));
        }
    }));

    public Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
        Firebase_Controller.getListOfPersons();
        Firebase_Controller.getListOfCompanies();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<Person> currentPersonsFromFirebase = new ArrayList<>();
        currentPersonsFromFirebase.addAll(Firebase_Controller.persons);

        List<Company> currentCompaniesFromFirebase = new ArrayList<>();
        currentCompaniesFromFirebase.addAll(Firebase_Controller.companies);

        List<PendingOrdersForAlgorythm> firstListOfPendingOrdersBUY = new ArrayList<>();
        List<PendingOrdersForAlgorythm> firstListOfPendingOrdersSELL = new ArrayList<>();

        for (int i = 0; i < currentPersonsFromFirebase.size(); i++) {
            if (currentPersonsFromFirebase.get(i).getPendingOrder() != null) {
                for (int j = 0; j < currentPersonsFromFirebase.get(i).getPendingOrder().size(); j++) {
                    if (currentPersonsFromFirebase.get(i).getPendingOrder().get(j).isBuyOrSell()) {
                        PendingOrdersForAlgorythm currentPendingOrder = new PendingOrdersForAlgorythm();
                        currentPendingOrder.setNameOfTheUser(currentPersonsFromFirebase.get(i).getLogin());
                        currentPendingOrder.setNameOfTheCompany(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).
                                getNameOfTheCompany());
                        currentPendingOrder.setAmount(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).getAmount());
                        currentPendingOrder.setBuyOrSell(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).isBuyOrSell());
                        currentPendingOrder.setTime(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).getTime());
                        currentPendingOrder.setCanOperate(true);
                        firstListOfPendingOrdersBUY.add(currentPendingOrder);
                    } else {
                        PendingOrdersForAlgorythm currentPendingOrder = new PendingOrdersForAlgorythm();
                        currentPendingOrder.setNameOfTheUser(currentPersonsFromFirebase.get(i).getLogin());
                        currentPendingOrder.setNameOfTheCompany(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).
                                getNameOfTheCompany());
                        currentPendingOrder.setAmount(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).getAmount());
                        currentPendingOrder.setBuyOrSell(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).isBuyOrSell());
                        currentPendingOrder.setTime(currentPersonsFromFirebase.get(i).getPendingOrder().get(j).getTime());
                        currentPendingOrder.setCanOperate(true);
                        firstListOfPendingOrdersSELL.add(currentPendingOrder);
                    }
                }
            }
        }

        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < firstListOfPendingOrdersBUY.size() - 1; i++) {
                if (firstListOfPendingOrdersBUY.get(i).getTime().getYear() > firstListOfPendingOrdersBUY.get(i + 1).getTime().getYear()) {
                    Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                    sorted = false;
                } else if (firstListOfPendingOrdersBUY.get(i).getTime().getYear() == firstListOfPendingOrdersBUY.get(i + 1).getTime().getYear()) {
                    if (firstListOfPendingOrdersBUY.get(i).getTime().getMonth() > firstListOfPendingOrdersBUY.get(i + 1).getTime().getMonth()) {
                        Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                        sorted = false;
                    } else if (firstListOfPendingOrdersBUY.get(i).getTime().getMonth() == firstListOfPendingOrdersBUY.get(i + 1).getTime().getMonth()) {
                        if (firstListOfPendingOrdersBUY.get(i).getTime().getDay() > firstListOfPendingOrdersBUY.get(i + 1).getTime().getDay()) {
                            Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                            sorted = false;
                        } else if (firstListOfPendingOrdersBUY.get(i).getTime().getDay() == firstListOfPendingOrdersBUY.get(i + 1).getTime().getDay()) {
                            if (firstListOfPendingOrdersBUY.get(i).getTime().getHour() > firstListOfPendingOrdersBUY.get(i + 1).getTime().getHour()) {
                                Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                                sorted = false;
                            } else if (firstListOfPendingOrdersBUY.get(i).getTime().getHour() == firstListOfPendingOrdersBUY.get(i + 1).getTime().
                                    getHour()) {
                                if (firstListOfPendingOrdersBUY.get(i).getTime().getMinute() > firstListOfPendingOrdersBUY.get(i + 1).getTime().
                                        getMinute()) {
                                    Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                                    sorted = false;


                                } else if (firstListOfPendingOrdersBUY.get(i).getTime().getMinute() == firstListOfPendingOrdersBUY.get(i + 1).
                                        getTime().getMinute()) {
                                    if (firstListOfPendingOrdersBUY.get(i).getTime().getSecond() > firstListOfPendingOrdersBUY.get(i + 1).getTime().
                                            getSecond()) {
                                        Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                                        sorted = false;
                                    } else if (firstListOfPendingOrdersBUY.get(i).getTime().getSecond() == firstListOfPendingOrdersBUY.get(i + 1).
                                            getTime().getSecond()) {
                                        if (firstListOfPendingOrdersBUY.get(i).getTime().getMillisecond() > firstListOfPendingOrdersBUY.get(i + 1).
                                                getTime().getMillisecond()) {
                                            Collections.swap(firstListOfPendingOrdersBUY, i, i + 1);
                                            sorted = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        boolean sorted2 = false;
        while (!sorted2) {
            sorted2 = true;
            for (int i = 0; i < firstListOfPendingOrdersSELL.size() - 1; i++) {
                if (firstListOfPendingOrdersSELL.get(i).getTime().getYear() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getYear()) {
                    Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                    sorted2 = false;
                } else if (firstListOfPendingOrdersSELL.get(i).getTime().getYear() == firstListOfPendingOrdersSELL.get(i + 1).getTime().getYear()) {
                    if (firstListOfPendingOrdersSELL.get(i).getTime().getMonth() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getMonth()) {
                        Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                        sorted2 = false;
                    } else if (firstListOfPendingOrdersSELL.get(i).getTime().getMonth() == firstListOfPendingOrdersSELL.get(i + 1).getTime().getMonth()) {
                        if (firstListOfPendingOrdersSELL.get(i).getTime().getDay() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getDay()) {
                            Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                            sorted2 = false;
                        } else if (firstListOfPendingOrdersSELL.get(i).getTime().getDay() == firstListOfPendingOrdersSELL.get(i + 1).getTime().getDay()) {
                            if (firstListOfPendingOrdersSELL.get(i).getTime().getHour() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getHour()) {
                                Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                                sorted2 = false;
                            } else if (firstListOfPendingOrdersSELL.get(i).getTime().getHour() == firstListOfPendingOrdersSELL.get(i + 1).getTime().getHour()) {
                                if (firstListOfPendingOrdersSELL.get(i).getTime().getMinute() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getMinute()) {
                                    Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                                    sorted2 = false;
                                } else if (firstListOfPendingOrdersSELL.get(i).getTime().getMinute() == firstListOfPendingOrdersSELL.get(i + 1).getTime().getMinute()) {
                                    if (firstListOfPendingOrdersSELL.get(i).getTime().getSecond() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getSecond()) {
                                        Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                                        sorted2 = false;
                                    } else if (firstListOfPendingOrdersSELL.get(i).getTime().getSecond() == firstListOfPendingOrdersSELL.get(i + 1).getTime().getSecond()) {
                                        if (firstListOfPendingOrdersSELL.get(i).getTime().getMillisecond() > firstListOfPendingOrdersSELL.get(i + 1).getTime().getMillisecond()) {
                                            Collections.swap(firstListOfPendingOrdersSELL, i, i + 1);
                                            sorted2 = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        List<PendingOrdersForAlgorythm> finalOneListOfPendingOrdersBUY = new ArrayList<>();

        for (int i = 0; i < firstListOfPendingOrdersBUY.size(); i++) {
            if (firstListOfPendingOrdersBUY.get(i).getAmount() == 1) {
                finalOneListOfPendingOrdersBUY.add(firstListOfPendingOrdersBUY.get(i));
            } else {
                int indexForBUYOneOrder = firstListOfPendingOrdersBUY.get(i).getAmount();
                for (int j = 0; j < indexForBUYOneOrder; j++) {
                    PendingOrdersForAlgorythm currentPendingOrder = new PendingOrdersForAlgorythm();
                    currentPendingOrder.setNameOfTheUser(firstListOfPendingOrdersBUY.get(i).getNameOfTheUser());
                    currentPendingOrder.setNameOfTheCompany(firstListOfPendingOrdersBUY.get(i).getNameOfTheCompany());
                    currentPendingOrder.setAmount(1);
                    currentPendingOrder.setBuyOrSell(firstListOfPendingOrdersBUY.get(i).isBuyOrSell());
                    currentPendingOrder.setTime(firstListOfPendingOrdersBUY.get(i).getTime());
                    currentPendingOrder.setCanOperate(true);
                    finalOneListOfPendingOrdersBUY.add(currentPendingOrder);
                }
            }
        }

        List<PendingOrdersForAlgorythm> finalOneListOfPendingOrdersSELL = new ArrayList<>();

        for (int i = 0; i < firstListOfPendingOrdersSELL.size(); i++) {
            if (firstListOfPendingOrdersSELL.get(i).getAmount() == 1) {
                finalOneListOfPendingOrdersSELL.add(firstListOfPendingOrdersSELL.get(i));
            } else {
                int indexForSELLOneOrder = firstListOfPendingOrdersSELL.get(i).getAmount();
                for (int j = 0; j < indexForSELLOneOrder; j++) {
                    PendingOrdersForAlgorythm currentPendingOrder = new PendingOrdersForAlgorythm();
                    currentPendingOrder.setNameOfTheUser(firstListOfPendingOrdersSELL.get(i).getNameOfTheUser());
                    currentPendingOrder.setNameOfTheCompany(firstListOfPendingOrdersSELL.get(i).getNameOfTheCompany());
                    currentPendingOrder.setAmount(1);
                    currentPendingOrder.setBuyOrSell(firstListOfPendingOrdersSELL.get(i).isBuyOrSell());
                    currentPendingOrder.setTime(firstListOfPendingOrdersSELL.get(i).getTime());
                    currentPendingOrder.setCanOperate(true);
                    finalOneListOfPendingOrdersSELL.add(currentPendingOrder);
                }
            }
        }

        List<PendingOrdersForAlgorythm> listForFuturePriceBUY = new ArrayList<>();
        listForFuturePriceBUY.addAll(finalOneListOfPendingOrdersBUY);

        List<PendingOrdersForAlgorythm> listForFuturePriceSELL = new ArrayList<>();
        listForFuturePriceSELL.addAll(finalOneListOfPendingOrdersSELL);

        for (int i = 0; i < finalOneListOfPendingOrdersBUY.size(); i++) {
            for (int j = 0; j < finalOneListOfPendingOrdersSELL.size(); j++) {
                if (finalOneListOfPendingOrdersBUY.get(i).getNameOfTheCompany().equals(finalOneListOfPendingOrdersSELL.get(j).getNameOfTheCompany()) && finalOneListOfPendingOrdersBUY.get(i).isCanOperate() && finalOneListOfPendingOrdersSELL.get(j).isCanOperate()) {

                    int indexOfPersonBuying = -1;
                    for (int k = 0; k < currentPersonsFromFirebase.size(); k++) {
                        if (currentPersonsFromFirebase.get(k).getLogin().equals(finalOneListOfPendingOrdersBUY.get(i).getNameOfTheUser())) {
                            indexOfPersonBuying = k;
                        }
                    }

                    int indexOfCompanyInPersonBuyingInStockOwned = -1;
                    for (int k = 0; k < currentPersonsFromFirebase.get(indexOfPersonBuying).getStockOwned().size(); k++) {
                        if (currentPersonsFromFirebase.get(indexOfPersonBuying).getStockOwned().get(k).getName().equals(finalOneListOfPendingOrdersBUY.
                                get(i).getNameOfTheCompany())) {
                            indexOfCompanyInPersonBuyingInStockOwned = k;
                        }
                    }

                    int indexOfPersonSelling = -1;
                    for (int k = 0; k < currentPersonsFromFirebase.size(); k++) {
                        if (currentPersonsFromFirebase.get(k).getLogin().equals(finalOneListOfPendingOrdersSELL.get(j).getNameOfTheUser())) {
                            indexOfPersonSelling = k;
                        }
                    }

                    int indexOfCompanyInPersonSellingInStockOwned = -1;
                    for (int k = 0; k < currentPersonsFromFirebase.get(indexOfPersonSelling).getStockOwned().size(); k++) {
                        if (currentPersonsFromFirebase.get(indexOfPersonSelling).getStockOwned().get(k).getName().equals(finalOneListOfPendingOrdersSELL.
                                get(j).getNameOfTheCompany())) {
                            indexOfCompanyInPersonSellingInStockOwned = k;
                        }
                    }

                    int indexOfCompanyInCompanies = -1;
                    for (int k = 0; k < currentCompaniesFromFirebase.size(); k++) {
                        if (currentCompaniesFromFirebase.get(k).getName().equals(finalOneListOfPendingOrdersBUY.get(i).getNameOfTheCompany())) {
                            indexOfCompanyInCompanies = k;
                        }
                    }

                    for (int k = 0; k < currentPersonsFromFirebase.get(indexOfPersonBuying).getPendingOrder().size(); k++) {
                        if (currentPersonsFromFirebase.get(indexOfPersonBuying).getPendingOrder().get(k).getNameOfTheCompany().
                                equals(finalOneListOfPendingOrdersBUY.get(i).getNameOfTheCompany()) &&
                                currentPersonsFromFirebase.get(indexOfPersonBuying).getPendingOrder().get(k).isBuyOrSell() ==
                                        finalOneListOfPendingOrdersBUY.get(i).isBuyOrSell()) {
                            if (currentPersonsFromFirebase.get(indexOfPersonBuying).getPendingOrder().get(k).getAmount() > 1) {
                                List<PendingOrder> pendingOrders = new ArrayList<>();
                                pendingOrders.addAll(currentPersonsFromFirebase.get(indexOfPersonBuying).getPendingOrder());
                                pendingOrders.get(k).setAmount(pendingOrders.get(k).getAmount() - 1);
                                currentPersonsFromFirebase.get(indexOfPersonBuying).setPendingOrder(pendingOrders);
                            } else {
                                currentPersonsFromFirebase.get(indexOfPersonBuying).getPendingOrder().remove(k);
                            }
                        }
                    }

                    for (int k = 0; k < currentPersonsFromFirebase.get(indexOfPersonSelling).getPendingOrder().size(); k++) {
                        if (currentPersonsFromFirebase.get(indexOfPersonSelling).getPendingOrder().get(k).getNameOfTheCompany().
                                equals(finalOneListOfPendingOrdersSELL.get(j).getNameOfTheCompany()) &&
                                currentPersonsFromFirebase.get(indexOfPersonSelling).getPendingOrder().get(k).isBuyOrSell() ==
                                        finalOneListOfPendingOrdersSELL.get(j).isBuyOrSell()) {
                            if (currentPersonsFromFirebase.get(indexOfPersonSelling).getPendingOrder().get(k).getAmount() > 1) {
                                List<PendingOrder> pendingOrders = new ArrayList<>();
                                pendingOrders.addAll(currentPersonsFromFirebase.get(indexOfPersonSelling).getPendingOrder());
                                pendingOrders.get(k).setAmount(pendingOrders.get(k).getAmount() - 1);
                                currentPersonsFromFirebase.get(indexOfPersonSelling).setPendingOrder(pendingOrders);
                            } else {
                                currentPersonsFromFirebase.get(indexOfPersonSelling).getPendingOrder().remove(k);
                            }
                        }
                    }

                    currentPersonsFromFirebase.get(indexOfPersonBuying).setCurrentMoney(round(currentPersonsFromFirebase.get(indexOfPersonBuying).
                            getCurrentMoney() - round(currentCompaniesFromFirebase.get(indexOfCompanyInCompanies).getStockPrices().
                            get(currentCompaniesFromFirebase.get(indexOfCompanyInCompanies).getStockPrices().size() - 1).getValue())));
                    currentPersonsFromFirebase.get(indexOfPersonBuying).getStockOwned().get(indexOfCompanyInPersonBuyingInStockOwned).
                            setAmount(currentPersonsFromFirebase.get(indexOfPersonBuying).getStockOwned().get(indexOfCompanyInPersonBuyingInStockOwned).
                                    getAmount() + 1);

                    currentPersonsFromFirebase.get(indexOfPersonSelling).setCurrentMoney(round(currentPersonsFromFirebase.get(indexOfPersonSelling).
                            getCurrentMoney() + round(currentCompaniesFromFirebase.get(indexOfCompanyInCompanies).getStockPrices().
                            get(currentCompaniesFromFirebase.get(indexOfCompanyInCompanies).getStockPrices().size() - 1).getValue())));
                    currentPersonsFromFirebase.get(indexOfPersonSelling).getStockOwned().get(indexOfCompanyInPersonSellingInStockOwned).
                            setAmount(currentPersonsFromFirebase.get(indexOfPersonSelling).getStockOwned().get(indexOfCompanyInPersonSellingInStockOwned).
                                    getAmount() - 1);

                    finalOneListOfPendingOrdersBUY.get(i).setCanOperate(false);
                    finalOneListOfPendingOrdersSELL.get(j).setCanOperate(false);
                }
            }
        }

        for (int i = 0; i < currentCompaniesFromFirebase.size(); i++) {
            String nameOfTheCurrentCompany = currentCompaniesFromFirebase.get(i).getName();
            int indexForBuyingStock = 0;
            int indexForSellingStock = 0;

            for (int j = 0; j < listForFuturePriceBUY.size(); j++) {
                if (listForFuturePriceBUY.get(j).getNameOfTheCompany().equals(nameOfTheCurrentCompany)) {
                    indexForBuyingStock += listForFuturePriceBUY.get(j).getAmount();
                }
            }

            for (int j = 0; j < listForFuturePriceSELL.size(); j++) {
                if (listForFuturePriceSELL.get(j).getNameOfTheCompany().equals(nameOfTheCurrentCompany)) {
                    indexForSellingStock += listForFuturePriceSELL.get(j).getAmount();
                }
            }

            List<StockPrice> listOfStockPrices = new ArrayList<>();
            listOfStockPrices.addAll(currentCompaniesFromFirebase.get(i).getStockPrices());

            double newStockPriceValue = listOfStockPrices.get(listOfStockPrices.size() - 1).getValue();
            newStockPriceValue *= ((indexForBuyingStock - indexForSellingStock) / (currentCompaniesFromFirebase.get(i).getAmountOfInstruments() * 10))
                    + Math.pow(1.0005, indexForBuyingStock -
                    indexForSellingStock);
            newStockPriceValue = round(newStockPriceValue);

            StockPrice newStockPrice = new StockPrice();
            newStockPrice.setValue(newStockPriceValue);

            Time timeOfNewStockPrice = new Time();
            timeOfNewStockPrice.setYear(LocalDateTime.now().getYear());
            timeOfNewStockPrice.setMonth(LocalDateTime.now().getMonthValue());
            timeOfNewStockPrice.setDay(LocalDateTime.now().getDayOfMonth());
            timeOfNewStockPrice.setHour(LocalDateTime.now().getHour());
            timeOfNewStockPrice.setMinute(LocalDateTime.now().getMinute());
            timeOfNewStockPrice.setSecond(LocalDateTime.now().getSecond());
            timeOfNewStockPrice.setMillisecond(ZonedDateTime.now().toInstant().toEpochMilli());
            newStockPrice.setTime(timeOfNewStockPrice);

            listOfStockPrices.add(newStockPrice);
            currentCompaniesFromFirebase.get(i).setStockPrices(listOfStockPrices);
        }

        Firebase_Controller.savePersons(currentPersonsFromFirebase);
        Firebase_Controller.saveCompanies(currentCompaniesFromFirebase);
    }));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BUTTON.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BUTTON.setStyle("-fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-radius: 3");
                BUTTON.setTextFill(Color.valueOf("#ffffff"));
            }
        });

        BUTTON.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BUTTON.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 3");
                BUTTON.setTextFill(Color.valueOf("#f56262"));
            }
        });

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();
    }

    @FXML
    private void goBUTTON() throws IOException {
        List<Person> newPersons = new ArrayList<>();
        List<Company> newCompanies = new ArrayList<>();
        List<String> logins = new ArrayList<>();
        logins.add("RainbowEye");
        logins.add("HappyCat");
        logins.add("YellowDragon");
        logins.add("LordOfTheRings");
        logins.add("SadTiger");
        logins.add("WonderfulMonkey");
        logins.add("LovelyTeddy");
        logins.add("EvilSnake");
        logins.add("RainyDay");
        logins.add("ColorfulWind");
        logins.add("zorro");
        logins.add("KingArthur");
        logins.add("BusStop");
        logins.add("pantera");
        logins.add("Radgoll");
        logins.add("SecretForest");
        logins.add("NOISYelefant");
        logins.add("atena");
        logins.add("calculator");
        logins.add("coldFreezer");

        List<String> passwords = new ArrayList<>();
        passwords.add("Castle123");
        passwords.add("123Kittens321");
        passwords.add("GoldenMoney");
        passwords.add("Sauron");
        passwords.add("Nile192");
        passwords.add("902Jungle");
        passwords.add("356Honey");
        passwords.add("326Mountain");
        passwords.add("Lacasa505");
        passwords.add("Pocahontas");
        passwords.add("Spain4Ever");
        passwords.add("Merlin1");
        passwords.add("bus321");
        passwords.add("blackCat");
        passwords.add("Super348Cat");
        passwords.add("SingingBird");
        passwords.add("Africa631");
        passwords.add("Antena");
        passwords.add("MA4");
        passwords.add("frozenIceCream567");

        List<StockOwned> stocksOwned = new ArrayList<>();
        stocksOwned.add(new StockOwned("Lernado", 10));
        stocksOwned.add(new StockOwned("Nike", 15));
        stocksOwned.add(new StockOwned("Apple", 8));
        stocksOwned.add(new StockOwned("Audi", 10));
        stocksOwned.add(new StockOwned("KFC", 11));
        stocksOwned.add(new StockOwned("Ryanair", 16));
        stocksOwned.add(new StockOwned("IKEA", 8));
        stocksOwned.add(new StockOwned("McDonald's", 6));

        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setCurrentMoney(1000);
            person.setLogin(logins.get(i));
            person.setMoney(6440.79);
            person.setPassword(passwords.get(i));
            person.setStockOwned(stocksOwned);
            newPersons.add(person);
        }

        List<String> names = new ArrayList<>();
        names.add("Lernado");
        names.add("Nike");
        names.add("Apple");
        names.add("Audi");
        names.add("KFC");
        names.add("Ryanair");
        names.add("IKEA");
        names.add("McDonald's");

        List<Integer> amount = new ArrayList<>();
        amount.add(200);
        amount.add(300);
        amount.add(160);
        amount.add(200);
        amount.add(220);
        amount.add(320);
        amount.add(160);
        amount.add(120);

        List<Double> prices = new ArrayList<>();
        prices.add(2.01);
        prices.add(33.04);
        prices.add(150.05);
        prices.add(84.89);
        prices.add(95.45);
        prices.add(123.93);
        prices.add(71.71);
        prices.add(44.88);

        Time time = new Time();
        time.setYear(2020);
        time.setMonth(6);
        time.setDay(25);
        time.setHour(22);
        time.setMinute(23);
        time.setSecond(44);
        time.setMillisecond(10);

        for (int i = 0; i < 8; i++) {
            Company company = new Company();
            company.setName(names.get(i));
            company.setAmountOfInstruments(amount.get(i));
            List<StockPrice> stockPrices = new ArrayList<>();
            StockPrice stockPrice = new StockPrice();
            stockPrice.setValue(prices.get(i));
            stockPrice.setTime(time);
            stockPrices.add(stockPrice);
            company.setStockPrices(stockPrices);
            newCompanies.add(company);
        }

        Firebase_Controller.savePersons(newPersons);
        Firebase_Controller.saveCompanies(newCompanies);
    }

    @FXML
    private void goLogOut() throws IOException {
        timeline1.stop();
        main.showLogin();
        Firebase_Controller.changeStatusOfAdminToFalse();
    }

    public static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}