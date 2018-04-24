import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		String line1, line2, line3 = "";
		ArrayList<Packet> packets = new ArrayList<Packet>(); //list of packets from dump file
		File f = null;
		BufferedReader br = null;
		String fileName = "./src/sampletcpdump.txt"; //name of dump file, remove /src if needed 
		
		f = new File(fileName); //make the file object
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while((line1 = br.readLine()) != null){
			line2 = br.readLine(); //reads too much here
			System.out.print(line1);
			
			//determining if send or receive
			String line2sub = line2.substring(0, line2.indexOf('>')).trim(); //getting ip address
			String[] mode = line2sub.split("."); //the length determines if send or receive
			
			if(mode.length == 4){ //receive; parse, find existing object, complete object
				
				//parse
				line3 = br.readLine(); //read third line bc receives are three lines
				String ip = mode[0] + "." + mode[1] + "." + mode[2] + "." + mode[3];
				String id = line3.substring(line3.indexOf("id")+3,
						line3.indexOf(",", line3.indexOf("id"))).trim(); //reading id
				double end = Double.parseDouble(line1.substring(0, line1.indexOf("I")).trim());
				
				//find packet with same id
				int target=-1; //debugging
				for(int i = 0; i<packets.size();i++){
					if(packets.get(i).getId() == id){
						target = i;
					}
				}
				//complete object
				packets.get(target).setEnd(end);
				packets.get(target).setIp(ip);
			}
			else if(mode.length == 5){ //send; parse, create object, add to list
				//parse
				String id = line1.substring(line1.indexOf("id")+3,
						line1.indexOf(",", line1.indexOf("id"))).trim(); //reading id
				if(!(id.equals("0"))){//not junk
					String ttl = line1.substring(line1.indexOf("ttl")+4,
							line1.indexOf(",", line1.indexOf("ttl"))).trim(); //reading ttl
					Double start = Double.parseDouble(line1.substring(0, line1.indexOf("I")).trim()); //reading id

					//create object
					Packet p = new Packet(ttl, id, start);
					packets.add(p); //add to list
				}//if
			}//elif
		}//while

		//print out packets
		Integer temp = 1; //the ttl we want
		boolean first;
		while(!(packets.isEmpty())){ //while not empty
			first = true;
			for(int i=0;i<packets.size();i++){ //iterate through packets once
				if(packets.get(i).getTtl().equals(temp.toString())){ //correct ttl
					Packet tempPacket = packets.get(i);
					if(first){
					System.out.println("TTL " + tempPacket.getTtl());
					System.out.println(tempPacket.getIp());
					first = false;
					}
					Double time = (tempPacket.getEnd() - tempPacket.getStart()) * 1000;
					System.out.println(time.toString() + " ms");
				}
			}
			temp++; //search for next ttl
		}
		
	}

}
