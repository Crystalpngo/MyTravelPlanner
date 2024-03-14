import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class MyTravelPlanner {
	
			
	
	public static String getPlan(LocalDate date, String weather, 
			LocalTime timeOfFirstAppointment, 
			LocalTime timeOfLastAppointment) {
		if (weather.equalsIgnoreCase("RAINY") || weather.equalsIgnoreCase("SNOWY")) {
			return "Please cancel or reschedule your appointments on " + formatDate(date) +".";
			
		}
		
		boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY 
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
		
		LocalDate localDate = LocalDate.now();

		LocalTime firstTrainWeekday = LocalTime.of(06,00);
		LocalTime lastTrainWeekday = LocalTime.of(23,00);
		LocalTime firstTrainWeekend = LocalTime.of(10,00); 
		LocalTime lastTrainWeekend = LocalTime.of(20,00);
		
		LocalTime firstTrain;
		if (isWeekend) {
			firstTrain = firstTrainWeekend;
					
		}else {
			firstTrain = firstTrainWeekday;
		}
		
		LocalTime lastTrain;
		if (isWeekend) {
			lastTrain = lastTrainWeekend;
		}else {
			lastTrain = lastTrainWeekday;
		}
		
	if (!timeOfFirstAppointment.isBefore(firstTrain.plusHours(1)) 
			&& !timeOfLastAppointment.plusHours(1).isAfter(lastTrain)) {
		//String departureTime = firstTrain.format(DateTimeFormatter.ofPattern("h:mm a"));
		timeOfFirstAppointment = timeOfFirstAppointment.truncatedTo(ChronoUnit.HOURS).plusMinutes(30 * (timeOfFirstAppointment.getMinute()/30));
		LocalTime departureTime = timeOfFirstAppointment.minusHours(2);
		String returnTime = timeOfLastAppointment.format(DateTimeFormatter.ofPattern("h:mm a"));		
		timeOfLastAppointment = timeOfLastAppointment.truncatedTo(ChronoUnit.HOURS).plusMinutes(30 * (timeOfLastAppointment.getMinute()/30)).plusMinutes(30);		
		
		if (departureTime.equals("10:00") || departureTime.equals("12:00") || departureTime.equals("14:00")|| departureTime.equals("16:00")|| departureTime.equals("18:00")|| departureTime.equals("20:00")) {
			return "Please take the " + departureTime + 
					" train to go to the city, and the " 
					+returnTime + " train to get back home on "
					+ formatDate(date) + ".";
		}else {
			return "Please take the " + departureTime.plusMinutes(60) + 
					" train to go to the city, and the " 
					+returnTime + " train to get back home on "
					+ formatDate(date) + ".";
		}
		
		//String returnTime = lastTrain.format(DateTimeFormatter.ofPattern("h:mm a"))
		
		
		
	}else {
		return "Please drive on " + formatDate(date)
		+ ", and leave the house at least an hour before your first appointment.";
	}
	}
	
	
	private static String formatDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
	}
			
	
public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	System.out.println("Please enter date of appointment: YYYY-MM-DD");
	String appointmentDate = scanner.nextLine();
	LocalDate dateOfPlan = LocalDate.parse(appointmentDate);
	System.out.println("Please enter current weather prediction on "+ dateOfPlan);
	String weather = scanner.nextLine();
	System.out.println("Please enter the first appointment start time (XX:XX):");
	LocalTime timeOfFirstAppointment = LocalTime.parse(scanner.nextLine());
	System.out.println("Please eneter the last appointment end time (XX:XX):");
	LocalTime timeOfLastAppointment = LocalTime.parse(scanner.nextLine());
	
	String plan = getPlan(dateOfPlan, weather, timeOfFirstAppointment,
			timeOfLastAppointment);
	System.out.println(plan);
	
}


}
