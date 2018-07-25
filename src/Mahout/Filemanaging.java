package Mahout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;








import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.umass.lastfm.*;
import de.umass.xml.DomElement;

public class Filemanaging {
	protected static String key = "KEY";
	protected static String user = "USER";
	protected String[] Userrec;

	public static void main(String args[]) throws FileNotFoundException {
		
		//Collection<Tag> u=getTags("Nothing Else Matters");
		//System.out.println(u.size());
		//String[] s =trovatrackdanome("Nothing Else Matters by Nirvana");
		//System.out.println(s[0]+"/"+s[1]);
		//ArrayList<String> collab=new ArrayList<String>();
		//userbased.addAll(getTopTracks("TIGERSNAKEMAN"));
		//userbased.add("Smells Like Teen Spirit");
		//userbased.add("Mr. Brightside");
		//userbased.add("Wonderwall");
		//userbased.add("Purple Haze");
		//userbased.add("Sunshine of Your Love");
		//userbased.add("Bohemian Rhapsody");
		//collab.add("Nothing Else Matters by Metallica");
		//collab.add("Breaking Up Again by Accept");
		//collab.add("Love Me Two Times by The Doors");
		//collab.add("Highway Star by Deep Purple");
		//collab.add("Good Times Bad Times by Led Zeppelin");
		//collab.add("One of These Days by Pink Floyd");
		//confrontatags (collab, getTopTracks("TIGERSNAKEMAN"));
		

		// QUESTE SONO LE RIGHE CHE HO USATO PER GENERARE IL DB
//ArrayList<User>result=generaperfriends("granpubah",1);//blues
//result.addAll(generaperfriends("nopuppet",1));//heavy metal
//result.addAll(generaperfriends("Natanaelbp",1));//70
//result.addAll(generaperfriends("tuadestina",1));//jazz
//result.addAll(generaperfriends("HazieI",1));//pop
//result.addAll(generaperfriends("Alchedoc",1));//rap
//result.addAll(generaperfriends("Elturtle",1));//rock
//result.addAll(generaperfriends("chronicplutonic",1));//country
//result.addAll(generaperfriends("IEatPoison",1));//techno
//result.addAll(generaperfriends("rhinowing",1));//dubstep
//generadb(result);
	}
	
	
	
public static Collection<Tag> getTags(String nome, String artista) {
			
	        Caller.getInstance().setUserAgent("tst");
	        Collection<Tag> tags=Track.getTopTags(artista,nome, key);
	        if (tags.isEmpty()==false) {
	        return tags;}
	        else{return new ArrayList<Tag>();}
	    }
		
public static Collection<Tag> getTags(String id) {
			
	        Caller.getInstance().setUserAgent("tst");

	        Collection<Tag> tags=Track.getTopTags(null,id, key);
	        if (tags.isEmpty()==false) {
	        	return tags;
	        }
	        else {return new ArrayList<Tag>();}
			
	    }
		
		
		
		
		public static Collection<Track> getLoved(String utente) {
			System.out.println("getloved");
        PaginatedResult<Track> loved=User.getLovedTracks(utente, key);
        if (loved.isEmpty()==false) {
    	Collection<Track> tracks=loved.getPageResults();
		System.out.println("getloved returna " +tracks.size());
        return tracks;
        }
        else {System.out.println("getloved returna niente");
        	return new ArrayList<Track>();}
		
    }

		
		public static Collection<Track> getRecent(String utente) {
			System.out.println("getrecent");
	        PaginatedResult<Track> loved=User.getRecentTracks(utente, key);
	        if (loved.isEmpty()==false) {
	    	Collection<Track> tracks=loved.getPageResults();
			System.out.println("getrecent returna " +tracks.size());
	        return tracks;
	        }
	        else {return null;}
			
	    }
		
		public static Collection<Track> getTopTracks(String utente) {
			System.out.println("gettoptracks");
	        Collection<Track> loved=User.getTopTracks(utente, key);
	        if (loved.isEmpty()==false) {
	    		System.out.println("gettop returna " +loved.size());
	        return loved;
	        }
	        else {return null;}
			
	    }
		
		public static Collection<User> getFriends(String utente) {
			try{
			System.out.println("getfriends");
	        PaginatedResult<User> loved=User.getFriends(utente, key);
	    	Collection<User> tracks=loved.getPageResults();
	    	if (tracks==null || tracks.isEmpty()){
	    		return new ArrayList<User>();
	    	}
	    	else{
	        return tracks;
	        }
			}
			catch(NullPointerException e){
				return new ArrayList<User>();
			}
			catch(NumberFormatException e){
				return new ArrayList<User>();
			}
	    }
		
		public static Collection<Tag> getTopTags(String utente) {
			System.out.println("getTopTags");
	        Collection<Tag> loved=User.getTopTags(utente, key);
	        if (loved.isEmpty()==false) {
	        return loved;
	        }
	        else {return null;}
			
	    }
		
		
		
		
		
		
	    public static List<Track> elaboraPlaylist(Collection<Playlist> play){
	    	List<Track> tracce= new ArrayList<Track>();
	    	System.out.println("elaboraplaylist");
	    	for(Playlist playlist : play){
	    		tracce.addAll(playlist.getTracks());
	    	}
	    	return tracce;
	    	
	    	
	    	
	    }
	        
	    public static ArrayList<User> generaperfriends(String utente, int count){
	    	if (count==0){
	    		ArrayList<User> result=new ArrayList<User>();
	    		Collection<User> friends=getFriends(utente);
	    		for(User use : friends){
	    			result.add(use);
	    		}
	    		return result;}
	    	else{	
	    	ArrayList<User> result= new ArrayList<User>();
	    	ArrayList<User> temp= new ArrayList<User>();
	    	Collection<User> amici= getFriends(utente);
	    	for(User ute : amici){
	    		result.add(ute);
	    		if(Math.random()>0.75){
	    		temp=generaperfriends(ute.getName(), count-1);
	    		for(User use : temp){
	    			if (!result.contains(use)){
	    				result.add(use);
	    			}
	    		}
	    		}
	    	}
	    	return result;
	    	
	    	}
	    	
	    	
	    }
	    
	    public static void attaccadb(String utente) throws FileNotFoundException{

	    	PrintStream outFile = new PrintStream(new FileOutputStream("Dataset.csv",true));
	    	Collection<Track> loved=getLoved(utente);
	    	for (Track tra : loved){
	    	outFile.append(utente+","+tra.getName()+" by "+ tra.getArtist())	; 
	    	outFile.println();
	    	}
	    		    	outFile.close();

	    }
	    public static void generadb(ArrayList<User> utenti) throws FileNotFoundException{

	    	PrintStream outFile = new PrintStream(new File("Dataset.csv"));
	    	System.out.println("Utenti nel DB: "+ utenti.size());
	    	for(User use: utenti){
	    	Collection<Track> loved=getLoved(use.getName());
	    	for (Track tra : loved){
	    	outFile.println(use.getName()+","+tra.getName()+" by "+tra.getArtist())	;    	
	    	}
	    	}
	    		    	outFile.close();
	    		    	System.out.println("Utenti nel DB: "+ utenti.size());
	    }
	    
	    
	    
			public static float elaboraTag(String utente, ArrayList<String> collaborative) throws FileNotFoundException {
				float percentage=0;
		        Caller.getInstance().setUserAgent("tst");
				Collection<Track> top= getTopTracks(utente);
				if (top==null){top = new ArrayList<Track>();}
				Collection<Track> lovedtr= getLoved(utente);
				Collection<String> loved2=new ArrayList<String>();
				for(Track t:lovedtr){
				loved2.add(t.getName());
				}
				Collection<Track> recenttr= Filemanaging.getRecent(utente);
				System.out.println("recent trovati");
				List<Tagpeso> result=new ArrayList<Tagpeso>();
				List<Tag> temp= new ArrayList<Tag>();
				List<Tag> loved= new ArrayList<Tag>();
				List<Tag> recent= new ArrayList<Tag>();
				int counter;
				if (lovedtr!=null){
					System.out.println("elaboroloved in tag");
				for(Track track:lovedtr){
					loved.addAll(Track.getTopTags(track.getArtist(), track.getName() ,key));
				}
				}
				if (recenttr!=null){
					System.out.println("elabororecent in tag");
				for(Track track:recenttr){
					recent.addAll(Track.getTopTags(track.getArtist(), track.getName() ,key));
				}
				}
				System.out.println("elaborotop in tag");
				for(Track track: top){
					Collection<Tag> tagg= Track.getTopTags(track.getArtist(), track.getName(), key);
					temp.addAll(tagg);
				}
				System.out.println("elaborotag top size "+ temp.size());
				while(temp.isEmpty()==false){
					Tag t=temp.get(0);
					counter=0;
					while(temp.remove(t)){
						counter++;
					}
					if(loved.contains(t)){counter=counter+2; loved.remove(t);}
					if(recent.contains(t)){counter=counter+1; recent.remove(t);}
				result.add(new Tagpeso(t,counter));	
				
					}
				System.out.println("elaborotag loved size "+ loved.size());
				while(loved.isEmpty()==false){
					Tag tag=loved.get(0);
					counter=0;
					while(recent.remove(tag)){
						counter=counter+1;
					}
						while(loved.remove(tag)){
							counter=counter+2;
							
						}
						result.add(new Tagpeso(tag,counter));
					}
				System.out.println("elaborotag recent size "+ recent.size());
				while(recent.isEmpty()==false){
					counter=0;
					Tag tag=recent.get(0);
					while(recent.remove(tag)){
						counter=counter+1;
					}
					result.add(new Tagpeso(tag, counter));
				}
				System.out.println("lavoro la lista di tag");
				ArrayList<Tagpeso> fine=new ArrayList<Tagpeso>();
				while (result.isEmpty()==false){
					Tagpeso campione=result.get(0);
					Tag camptag=campione.getTag();
					counter=campione.getPeso();
					int c=1;
					while(c<result.size()){
						Tagpeso altra=result.get(c);
						if (campione.equals(altra)){
							counter=counter+altra.getPeso();
							result.remove(c);
						}
						c++;
					}
					fine.add(new Tagpeso(camptag,counter));
					result.remove(0);
					
				}
				System.out.println("ora faccio sort");
				Collections.sort(fine, new TagpesoComparator());
				
				
				System.out.println("elaborofile");
				PrintStream outFile = new PrintStream(new File("Tags.txt"));
				if (fine.isEmpty()){
					PrintStream outFile2 = new PrintStream(new File("RecommendTags.txt"));
					outFile2.println("nessun utente con quel nome o nessun dato per quell'utente");
					outFile2.close();
					outFile.println("nessun utente con quel nome o nessun dato per quell'utente");
					outFile.close();}
				else{
		        for(int c=0; (c<fine.size()&&c<20);c++ ){
		        	Tagpeso tagpeso=fine.get(c);
		        outFile.println(tagpeso.getTag().getName() + " // " + tagpeso.getPeso() );
                
		        }
                outFile.close();
                
			percentage=similipertag(fine,loved2,collaborative);
				}
		   return percentage;     
		}
			public static float similipertag(ArrayList<Tagpeso> arg, Collection<String> loved, ArrayList<String> collaborative){
				float percentage=0;
				int count=0;
				ArrayList<Track> result=new ArrayList<Track>();
				ArrayList<ArrayList<Track>> pippo= new ArrayList<ArrayList<Track>>();
				
				for(int i=0; i<20;i++){
					Tag t=arg.get(i).getTag();
					count=count+arg.get(i).getPeso();
			    pippo.add(gettrackpertag(t));	
					
				}
				
				double cost=count/20;
				for(int i=0; i<pippo.size();i++){
					double res=arg.get(i).getPeso();
					int c=0;
					while(res>0 && c<pippo.get(i).size()&& result.size()<20){
						
						if(
								(!result.contains(pippo.get(i).get(c).getName()+ " by "+pippo.get(i).get(c).getArtist())
								&& (!(loved.contains(pippo.get(i).get(c).getName()))))
								)
								{
							result.add(pippo.get(i).get(c));}
						res=res-cost;
						c++;
					}
				}
				percentage=confrontatags(collaborative,result);
				try {
					PrintStream outFile = new PrintStream(new File("RecommendTags.txt"));
					outFile.println("Raccomandazioni tramite le tue tag: ");
					for(Track t: result){
						outFile.println(t.getName()+" by "+t.getArtist());
					}
				outFile.close();
				}
				catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return percentage;
			}
			public static ArrayList<Track> gettrackpertag(Tag a){
				ArrayList<Track> result= new ArrayList<Track>();
				Collection<Track> tmp=Tag.getTopTracks(a.getName(), key);
				if (tmp==null){tmp=new ArrayList<Track>();
				return ((ArrayList<Track>)tmp);}
				else{
				result.addAll(tmp);
						return result;
				}
				
			}
			public static float confrontatags (ArrayList<String> collaborative, Collection<Track> userbased){
				if (collaborative==null ||  userbased==null || collaborative.isEmpty() || userbased.isEmpty() ){return 0;}
				ArrayList<Stringpeso> collares= new ArrayList<Stringpeso>();
				ArrayList<Stringpeso> userres=new ArrayList<Stringpeso>();
				ArrayList<String>usertemp=new ArrayList<String>();
				ArrayList<String>collatemp=new ArrayList<String>();
				int counter=0;
				float percentage=0;
				for(Track t: userbased){
				Collection<Tag> tem= Track.getTopTags(t.getArtist(), t.getName(), key);
				for (Tag ta:tem){
					usertemp.add(ta.getName());
				}
				}
				for(String s: collaborative){
					String[] str=separanomi(s);
					Collection<Tag> tem= getTags(str[0],str[1]);
					for (Tag ta:tem){
						collatemp.add(ta.getName());
					}
				}
				while(usertemp.isEmpty()==false){
					counter=0;
					String tag=usertemp.get(0);
					while(usertemp.remove(tag)){
						counter=counter+1;
					}
					userres.add(new Stringpeso(tag, counter));
				}
				while(collatemp.isEmpty()==false){
					counter=0;
					String tag=collatemp.get(0);
					while(collatemp.remove(tag)){
						counter=counter+1;
					}
					collares.add(new Stringpeso(tag, counter));
				}
				
				Collections.sort(collares, new StringpesoComparator());
				Collections.sort(userres, new StringpesoComparator());
				
				
				int limit=20;
				
				ArrayList<Stringpeso> collab=new ArrayList<Stringpeso>();
				System.out.println("TAG COLLABORATIVE");
				for(int i=0; i<collares.size() && i<(limit*3);i++){
					
					System.out.println(collares.get(i).getTag()+" "+collares.get(i).getPeso());
					collab.add(collares.get(i));
				}
				//System.out.println("COLLAB.SIZE = "+collab.size());
				ArrayList<Stringpeso> user=new ArrayList<Stringpeso>();
				System.out.println("TAG USERMODEL");
				for(int i=0; i<userres.size() && i<limit;i++){
					System.out.println(userres.get(i).getTag()+" "+userres.get(i).getPeso());
					user.add(userres.get(i));
				}
				//System.out.println("USER.SIZE = "+user.size());
				float addpercent=0;
				for(Stringpeso u: user){
					if (collab.contains(u)){
						Stringpeso c= collab.get(collab.lastIndexOf(u));
						if (u.getPeso()>c.getPeso()){
							addpercent=(((float)c.getPeso()/(float)u.getPeso()))/user.size();
							
						}
						else{
							
							addpercent=(((float)u.getPeso()/(float)c.getPeso()))/user.size();
						}
						//System.out.println(u.getPeso()+" "+c.getPeso()+ " " + addpercent);
						percentage=percentage+addpercent;
					}
				}
				percentage=percentage*100;
				
				//System.out.println(percentage);

				
			return percentage;
			
			}
			
			
			public static String[] separanomi (String na){
				String[] parts = na.trim().split("\\s+");
				String nomefinale=parts[0];
				String autore=null;
				int flag=0;
				for(int i=1;i<parts.length;i++){
					if (flag==1){
						if (autore==null){autore=parts[i];}
						else{
						autore=autore +" "+parts[i];}
						}	
						
					
					else if (!parts[i].equals("by")){nomefinale=nomefinale+" "+parts[i];}
					
					else{flag=1;}
				}
				String[] re=new String[2];
				re[0]=nomefinale;
				re[1]=autore;
				return re;
			}
			
			
			public static String[] trovatrackdanome (String na){
				String[] parts = na.trim().split("\\s+");
				String nomefinale=parts[0];
				String autore=null;
				int flag=0;
				for(int i=1;i<parts.length;i++){
					if (flag==1){
						if (autore==null){autore=parts[i];}
						else{
						autore=autore +" "+parts[i];}
						}	
						
					
					else if (!parts[i].equals("by")){nomefinale=nomefinale+" "+parts[i];}
					
					else{flag=1;}
				}
				System.out.println(nomefinale);
				Map<String, String> params = new HashMap<String, String>();
						params.put("track", nomefinale);
						params.put("limit", String.valueOf(1));
						Result result = Caller.getInstance().call("track.search", key, params);
						if(!result.isSuccessful()){return null;}
						DomElement element=result.getContentElement();
						DomElement matches=element.getChild("trackmatches");
						List<DomElement> tracks=matches.getChildren();
						if (tracks.isEmpty()){return null;}
						
						DomElement primatrack=tracks.iterator().next();
						String nometrack= primatrack.getChildText("name");
String[] re= new String[2];
re[0]=nometrack;
re[1]=autore;

return re;

			}
}
