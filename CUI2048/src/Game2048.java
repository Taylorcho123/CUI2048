// 2048������ CUI�����Դϴ�.

import java.util.Scanner;
import java.util.Random;

public class Game2048 {
	public static Scanner scan = new Scanner(System.in);
	
	public static int[][] block = {
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0}
	};	//���� ���
	
	public static void graphic() {
		System.out.println("��������������������������������������������������");
		System.out.printf("��%5d��%5d��%5d��%5d��\n",block[0][0],block[0][1],block[0][2],block[0][3]);
		System.out.println("��������������������������������������������������");
		System.out.printf("��%5d��%5d��%5d��%5d��\n",block[1][0],block[1][1],block[1][2],block[1][3]);
		System.out.println("��������������������������������������������������");
		System.out.printf("��%5d��%5d��%5d��%5d��\n",block[2][0],block[2][1],block[2][2],block[2][3]);
		System.out.println("��������������������������������������������������");
		System.out.printf("��%5d��%5d��%5d��%5d��\n",block[3][0],block[3][1],block[3][2],block[3][3]);
		System.out.println("��������������������������������������������������");
	}
	
	public static void exit_graphic() {
		
		for(int i=0; i<3; i++) {
			try {
				Thread.sleep(1000); 	// 1�ʾ� ����
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf(".");
		}	
	}
	
	public static void b_random(int total) {
		Random r = new Random();	// Random ������
		int pos = r.nextInt(total) +1;
		int b_size = r.nextInt(2) +1;	// create 2 or 4 size block
		int count=0;
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(block[i][j] == 0) {
					count++;
					if(count == pos) {
						block[i][j] = b_size *2;
					}
				}
			}
		}
	}
	
	public static int count_empty() {
		int count=0;
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(block[i][j] == 0)
					count++;
			}
		}
		return count; 
	}
	
	public static int win_fail() {	// 0 = Gameover, 1 = User is doing this game, 2 = User makes 2048!
		if(count_empty() == 0) {	// ����! �÷��̾ 2048�� ������� �ϴ���, ������� ���ٸ� ���� ������.
			return 0;				// ���� 0�� ���� �˻��ϴ� ���ǹ��� ���� �켱������ �д�.
		}
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(block[i][j] == 2048) {
					return 2;
				}
			}
		}
		return 1; // default
	}
	
	public static void sum_up() {
		int offs1=0, offs2=1;	// block[][]�� �ִ� variable�鳢�� accumulate, gravitate�ϴµ� ����� offsets
		
		/* accumulation */
		for(int k=0; k<4; k++) {		
			for(offs1=0; offs1<3; offs1++) {
				for(offs2=offs1+1; offs2<4; ) {
					if(block[offs1][k] == block[offs2][k]) {
						block[offs1][k] *= 2;
						block[offs2][k] = 0;
						
						offs1++;	// offs1�� for���� �������� ���ؼ� �ѹ� �� ����, ���������� 2�� ������.
						break;
					}
					/* �ƹ��� ���� �����°� ���� ũ���� ���� ��򰡿� �ִٰ� �ϴ���,
					 * �ָ� �����������鼭 �ֺ� ���� ���� ���� �ƴ϶�� 
					 * ��ֹ� ������ ��. */
					else if(block[offs1][k] > 0 && block[offs2][k] > 0) { 
						break;
					}
					else {
						offs2++;
					}
				}
			}
		}
		
		/* gravitate */
		for(int k=0; k<4; k++) {
			for(offs1=0; offs1<3; offs1++) {
				if(block[offs1][k] != 0)
					continue;
				for(offs2=offs1+1; offs2<4; offs2++) {
					if(block[offs2][k] > 0) {		// offs1�� block�� 0�� �� ���Խ�Ŵ
						block[offs1][k] = block[offs2][k];
						block[offs2][k] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void sum_down() {
		int offs1=3, offs2=2;	// block[][]�� �ִ� variable�鳢�� accumulate, gravitate�ϴµ� ����� offsets
		
		/* accumulation */
		for(int k=3; k>=0; k--) {		
			for(offs1=3; offs1>=1; offs1--) {
				for(offs2=offs1-1; offs2>=0; ) {
					if(block[offs1][k] == block[offs2][k]) {
						block[offs1][k] *= 2;
						block[offs2][k] = 0;
						
						offs1--;	// offs1�� for���� �������� ���ؼ� �ѹ� �� ����, ���������� 2�� ������.
						break;
					}
					/* �ƹ��� ���� �����°� ���� ũ���� ���� ��򰡿� �ִٰ� �ϴ���,
					 * �ָ� �����������鼭 �ֺ� ���� ���� ���� �ƴ϶�� 
					 * ��ֹ� ������ ��. */
					else if(block[offs1][k] > 0 && block[offs2][k] > 0) { 
						break;
					}
					else {
						offs2--;
					}
				}
			}
		}
		
		/* gravitate */
		for(int k=3; k>=0; k--) {
			for(offs1=3; offs1>=1; offs1--) {
				if(block[offs1][k] != 0)
					continue;
				for(offs2=offs1-1; offs2>=0; offs2--) {
					if(block[offs2][k] > 0) {		// offs1�� block�� 0�� �� ���Խ�Ŵ
						block[offs1][k] = block[offs2][k];
						block[offs2][k] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void sum_left() {
		int offs1=0, offs2=1;	// block[][]�� �ִ� variable�鳢�� accumulate, gravitate�ϴµ� ����� offsets
		
		/* accumulation */
		for(int k=0; k<4; k++) {		
			for(offs1=0; offs1<3; offs1++) {
				for(offs2=offs1+1; offs2<4; ) {
					if(block[k][offs1] == block[k][offs2]) {
						block[k][offs1] *= 2;
						block[k][offs2] = 0;
						
						offs1++;	// offs1�� for���� �������� ���ؼ� �ѹ� �� ����, ���������� 2�� ������.
						break;
					}
					/* �ƹ��� ���� �����°� ���� ũ���� ���� ��򰡿� �ִٰ� �ϴ���,
					 * �ָ� �����������鼭 �ֺ� ���� ���� ���� �ƴ϶�� 
					 * ��ֹ� ������ ��. */
					else if(block[k][offs1] > 0 && block[k][offs2] > 0) { 
						break;
					}
					else {
						offs2++;
					}
				}
			}
		}
		
		/* gravitate */
		for(int k=0; k<4; k++) {
			for(offs1=0; offs1<3; offs1++) {
				if(block[k][offs1] != 0)
					continue;
				for(offs2=offs1+1; offs2<4; offs2++) {
					if(block[k][offs2] > 0) {		// offs1�� block�� 0�� �� ���Խ�Ŵ
						block[k][offs1] = block[k][offs2];
						block[k][offs2] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void sum_right() {
		int offs1=3, offs2=2;	// block[][]�� �ִ� variable�鳢�� accumulate, gravitate�ϴµ� ����� offsets
		
		/* accumulation */
		for(int k=3; k>=0; k--) {		
			for(offs1=3; offs1>=1; offs1--) {
				for(offs2=offs1-1; offs2>=0; ) {
					if(block[k][offs1] == block[k][offs2]) {
						block[k][offs1] *= 2;
						block[k][offs2] = 0;
						
						offs1--;	// offs1�� for���� �������� ���ؼ� �ѹ� �� ����, ���������� 2�� ������.
						break;
					}
					/* �ƹ��� ���� �����°� ���� ũ���� ���� ��򰡿� �ִٰ� �ϴ���,
					 * �ָ� �����������鼭 �ֺ� ���� ���� ���� �ƴ϶�� 
					 * ��ֹ� ������ ��. */
					else if(block[k][offs1] > 0 && block[k][offs2] > 0) { 
						break;
					}
					else {
						offs2--;
					}
				}
			}
		}
		
		/* gravitate */
		for(int k=3; k>=0; k--) {
			for(offs1=3; offs1>=1; offs1--) {
				if(block[k][offs1] != 0)
					continue;
				for(offs2=offs1-1; offs2>=0; offs2--) {
					if(block[k][offs2] > 0) {		// offs1�� block�� 0�� �� ���Խ�Ŵ
						block[k][offs1] = block[k][offs2];
						block[k][offs2] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		int inp;
		char Input;
		
		System.out.println("CUI���� ����2048�� �����մϴ�.");
		System.out.println("wŰ�� ��, sŰ�� �Ʒ�, aŰ�� ����, dŰ�� �������Դϴ�.");
		System.out.println("\"->\"ȭ��ǥ ���� �ش� ���ڸ� �Է��ϰ� ���͸� �����ּ���~");
	
		b_random(count_empty());	// �������� 2�� 4����� �ִ´�.
		
		while(win_fail() == 1) {		// ���� ��� ����� 0�� �ƴ� ��, ���ӿ����ȴ�.
			b_random(count_empty());
			graphic();			// ���ȭ���� ����Ѵ�.
			System.out.printf("-> ");
			Input = scan.next().charAt(0); 	// Ű����� ���� �Է� ����
			
			switch(Input) {
			case 'w':	// up
				sum_up();
				break;
			case 's':	// down
				sum_down();
				break;
			case 'a':	// left
				sum_left();
				break;
			case 'd':	// right
				sum_right();
				break;
			default :	// �߸��� ���ڸ� �Է����� ��
				System.out.println("�߸��� ���ڸ� �Է��ϼ̽��ϴ�.\n\"w,a,s,d\"�߿��� �ٽ� �Է����ּ���.");
			}
			
			if(win_fail() == 2) {
				System.out.println("2048�� �޼��ϼ̽��ϴ�. �����մϴ�~\n������ ����Ͻðڽ��ϱ�?(yes = 1, no = 2)");
				System.out.printf("-> ");
				inp = scan.nextInt();
				
				if(inp == 2) {
					System.out.println("������ �����մϴ�");
					exit_graphic();
					break;
				}
			}
			
			if(win_fail() == 0) {
				System.out.println("Game Over");
				exit_graphic();
				break;
			}
		}
	}
}
