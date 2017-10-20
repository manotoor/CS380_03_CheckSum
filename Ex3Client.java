import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ex3Client{
	public static void main(String[] args){
		//Basic socket stuff
		try(Socket socket = new Socket("18.221.102.182",38103)){
			System.out.println("Connected to Server");

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			//Read in int value of how big the array is
			int size = is.read();
			System.out.println("Reading " + size + " bytes.");
			byte[] values = new byte[size];
			//Read in array and print to screen
			System.out.print("Bytes Read: ");
			for(int i =0; i < size;i++){
				values[i] = (byte)is.read();
				if((i%10)==0)
					System.out.print("\n\t");
				System.out.printf("%02X",values[i]);
			}
			//Checksum calculations
			short checkSum = checksum(values);
			System.out.printf("\nChecksum calculated: 0x%02X\n",checkSum);
			//Send Checksum back to server as 2 bytes
			os.write(checkSum >> 8);
			os.write(checkSum >> 0);
			//Listen for response, if response 1 then good if 0 then bad
			if(is.read() == 1){
				System.out.println("Response is Good.");
			}else{
				System.out.println("Response is Bad.");
			}
		}catch(Exception e){
			System.out.println("\nSomething went wrong with server\n" + e);
		}
		System.out.println("\nDisconnected from Server");
	}
	private static short checksum(byte[] b){
		//Initialize Sum
		int sum = 0;
		//int i = 0;
		//Loop through bytes
		for(int i =0; i < b.length-1; i= i+2){
			//take upper shift 4 bits AND wit
			byte upper = b[i];
			byte lower = b[i+1];
			//add to sum
			sum = sum + ((upper << 8 & 0xFF00) + (lower & 0xFF));
			//check to make sure no overflow
			if ((sum & 0xFFFF0000) > 0) {
				sum &= 0xFFFF;
				sum++;
			}
		}
		//For Odd
		if(b.length %2 == 1){
			//add odd bit to sum
			sum = sum + ((b[b.length-1] << 8) & 0xFF00);
			
			// Check overflow
			if((sum & 0xFFFF0000) > 0) {
				sum &= 0xFFFF;
				sum++;
			}
		}
		//return sum
		return (short)~(sum & 0xFFFF);
	}
}