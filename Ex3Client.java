public class Ex3Client.java{
	public static void main(String[] args){
		//Basic socket stuff
		try(Socket socket = new Socket("18.221.102.182",38103)){
			InputStream is = socket.getInputStream();
			OuputStream os = socket.getOutputStream();

			//Read in int value of how big the array is
			int size = is.read();
			byte[] values = new byte[size];
			//Read in array and print to screen
			for(int i =0; i < size;i++){
				values[i] = (byte)is.read();
				if((i%10)==0)
					System.out.print("\n");
				System.out.printf("%02X",values[i]);
			}
			//Checksum calculations
			short checkSum = checksum(values);
			//Send Checksum back to server as 2 bytes

			//Listen for response, if response 1 then good if 0 then bad


			System.out.println("Connected to Server");
		}catch(Exception e){
			System.out.println("Something went wrong with server\n" + e);
		}
		System.out.println("Disconnected from Server");
	}
	private static short checksum(byte[] b){
		//Initialize Sum
		//Loop through bytes
			//while looping i/2
			//take upper shift logical left 4 bits
			//take next byte and add to uppper
			//put back in result
			//add to sum
			//check to make sure no overflow
		//return sum
	}
}