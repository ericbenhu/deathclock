import java.util.Random;



public class main {
	Player one = new Player(20, new Card[]{null, new vee(),null, new vo(),null, null});
	int t;
	public static void main(String[] args)
	{
		main m = new main();
  
		long lastTime=System.currentTimeMillis();
		long delta;
		while(true)
		{
			if ((delta=System.currentTimeMillis()-lastTime)>1000) {
				System.out.println("deck:"+m.one.Deck.list());
				System.out.println("hand:"+m.one.Hand.list());
				m.one.Deck.sendCard(m.one.Hand,0,-1);
				lastTime=System.currentTimeMillis();
			}
		}

 
	}
	class Player
	{
		Random Rand= new Random();
		int turns,rand,morale;
		Pile Deck, Extra, Hand, Grave, Oblivion;
		boolean win,lose;
		public Player(int turns, Card[] Deck)
		{
			this.turns=turns;
			this.Deck=new Pile(Deck);
			Hand=new Pile(2);
			Grave = new Pile(40);
			Oblivion= new Pile(40);
		}
		public boolean draw()
		{
			if(!Deck.sendCard(Hand,0,-1))lose=true;
			return true;
		}
		
	}
	abstract class Card
	{
		private int value,cost,refund;
		private int hp,stam;
		private skill[] skills;
		private effect[] effects;
		private String[] types, subtypes, clans;
		public Card(){} 
		public String toString() {return getClass().getSimpleName() ;}
	}
    abstract class effect{
    	private String[] triggers;
    	private Card user;
    	private Card[] targets;
    }
    abstract class skill{
    	private int rating,castTime,stamina,aggro;
    	private GridPt[] telegraph;
    	private Card user;
    	private Card[] targets;
    }
    class GridPt{ //for skirmishes
    	public int x,y;
    	public GridPt(int x, int y){
    		this.x=x;
    		this.y=y;
    	}
    }
    class HexPt{ //for map,meant to be relative
    	public int l,r; //upleft,upright
    	public HexPt(int l, int r){
    		this.l=l;
    		this.r=r;
    	}
    	public HexPt plus(HexPt p){
    		return new HexPt(l+p.l,r+p.r);
    	}
    	public HexPt minus(HexPt p){
    		return new HexPt(l-p.l,r-p.r);
    	}//blah
    	public HexPt neg(){
    		return new HexPt(-l,-r);
    	}
    	public int distance(){
    		return Math.abs(l)+Math.abs(r)-Math.min(Math.abs(l),Math.abs(r));
    	}
    }
	class vee extends Card
	{
		public vee() {super();}
	}
	class vo extends Card
	{
		public vo() {super();}
	}
	class Pile
	{
		Card[] cards;
		public Pile(Card[] pile)
		{
			cards=pile;
		}
		public Pile(int size)
		{
			cards=new Card[size];
		}
		public boolean shift(int dist, int start)
		{
			boolean success=true;
			int place=start;
			Card temp = null;
			collapse();
	 //assume nulls follow cards due to collapse
			int last=cards.length-dist-1;
			try{
				if(cards[start]==null)
				{success=true;}
				else if(cards[last]!=null)
				{ 
					System.out.println("hai"); 
					for(int a=start;a<dist;a++)
						for(int i=last; i>=0; i--){    
							if(i==last) temp = cards[last];
							if(i!=0)
								cards[i]=cards[i-1];
							else
								cards[0]=temp;
						}
				}
				else success=false;
			}
			catch(Exception e){System.out.println("can't shift");return false;}
			return success;
		}
		public boolean addCard(Card c, int toPos)
		{
			boolean success=false;
			if(toPos==-1)
				toPos=count();
			if(success=shift(1,toPos)){
				cards[toPos]=c;
			}
			return success;
		}
		public boolean sendCard(Pile to, int fromPos, int toPos)
		{
			boolean success=false;
			if (fromPos==-1)
				fromPos=count()-1;
			if(to.addCard(cards[fromPos],toPos)){
				cards[fromPos]=null;
				collapse();}
			return true;
		}
 
		public int count()
		{
			int c=0;
			for(Card cd: cards) if (cd!=null) c++; 
			return c;
		}
		public int nextCard(int first)
		{
			int s=first;
			while(cards[s]==null && s<count()) s++;
			if(s>=count()) return -1;
			return s;
		}
		public int nextEmpty(int first)
		{
			int s=first;
			while(cards[s]!=null && s<count()) s++;
			if(s<count()) return -1;
			return s;
		}
		public void collapse()
		{
			int Count = count();
			int n=0;
			Pile temp = new Pile(cards.length);
			for(int i=0;i<cards.length;i++)
			{
				if(cards[i]!=null) {temp.cards[n]=cards[i];n++;}
				if (n>=count()) break;
			}
			cards=temp.cards;
		}
		public void shuffle()
		{
			collapse();
			int Count = count();
			Pile temp = new Pile(cards.length);
			Random rand = new Random();
			boolean[] used =  new boolean[Count];
			for(int i=0;i<Count;i++)
			{
				int toPick=rand.nextInt(Count);
				if(!used[toPick]){
					temp.cards[toPick]=cards[i];
					used[toPick]=true;
				}
				else
					i--;
			}
			cards=temp.cards;
		}
		public String list()
		{
		String conct= new String();
		for(Card c:cards)
		{
			if(c!=null) conct += c.toString();
			else conct+="null ";
		}
		return conct;
		}
 //HERE'S A COMMENT AT THE END
// IMA FIRING MY LASER!
		// Here's more comment
		//moar comments!
		// Ima firing this comment to the heavens
	}
}
