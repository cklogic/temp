import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/** 
* @author LingChuan 
* @date 2017年10月23日 上午11:16:24 
*  
*/
public class GA {
	static class TT{
		String s;
		double d;
		public String getS() {
			return s;
		}
		public void setS(String s) {
			this.s = s;
		}
		public double getD() {
			return d;
		}
		public void setD(double d) {
			this.d = d;
		}
		
	}
	public static void main(String[] args) {
		
		 //f(x)=x+10sin(5x)+7cos(4x);
		
		TT tt[]=new TT[100];
		//初始化
		for (int i = 0; i < tt.length; i++) {
			char c[]=new char[17];
			Random rand =new Random();
			for (int j = 0; j < c.length; j++) {
				int r2=rand.nextInt(100);
				if(r2>49) {
					c[j]='1';
				}else {
					c[j]='0';
				}
			}
			TT t=new TT();
			t.s=String.copyValueOf(c);
			tt[i]=t;
		}
		//进化
		for (int i = 0; i < 50; i++) {
			//计算结果
			for (int j = 0; j < tt.length; j++) {
				double x=B2D(tt[j].s);
				double f=x + 10.0*Math.sin(5*x)+7*Math.cos(4*x);
				tt[j].d=f;
			}
			//精英排序
			Arrays.sort(tt,Comparator.comparing(TT::getD));
			//取排名前50%的精英繁殖
			for (int j = 50; j < 100; j++) {
				//产生两个后代
				String ts[] = breed(tt[j].s,tt[j+1].s);
				String v1=ts[0];
				String v2=ts[1];
				//0.1的概率变异
				Random rand =new Random();
				int r1=rand.nextInt(100);
				if(r1<10) {
					v1=variation(ts[0]);
					v2=variation(ts[0]);
				}
				tt[j-50].s=v1;//干掉前50%的淘汰者
				int r2=rand.nextInt(100);//父亲和父亲随机死掉一个,并被孩子2代替
				if(r2<50) {
					tt[j].s=v2;
				}else {
					tt[j+1].s=v2;
				}
				j=j+1;
			}
		}
		System.out.println(B2D(tt[tt.length-1].s));
		System.out.println(tt[tt.length-1].d);
	}
	//繁殖
	public static String[] breed(String sa,String sb) {
		
		String sa1=sa.substring(0, 8);
		String sa2=sa.substring(8, 17);
		
		String sb1=sb.substring(0, 8);
		String sb2=sb.substring(8, 17);
		
		String sc1=sa1+sb2;
		String sc2=sb1+sa2;
		String[] ss={sc1,sc2};
		return ss;
	}
	//变异
	public static String variation(String s) {
		char ts[] = s.toCharArray();
		Random rand =new Random();
		for (int i = 0; i < 3; i++) {
			int r1=rand.nextInt(17);
			int r2=rand.nextInt(100);
			if(r2>49) {
				ts[r1]='1';
			}else {
				ts[r1]='0';
			}
		}
		String rs=String.copyValueOf(ts);
		return rs;
	}
	//二进制转十进制
	public static double B2D(String s) {
		int x= 0 + Integer.valueOf(s, 2);
		double x2= 0 + (x/131071.0)*9;
		return x2;
	}
}
