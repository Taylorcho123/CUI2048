// 2048게임의 CUI버전입니다.

import java.util.Scanner;
import java.util.Random;

public class Game2048 {
	public static Scanner scan = new Scanner(System.in);
	
	public static int[][] block = {
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0}
	};	//게임 블록
	
	public static void graphic() {
		System.out.println("┌─────┬─────┬─────┬─────┐");
		System.out.printf("│%5d│%5d│%5d│%5d│\n",block[0][0],block[0][1],block[0][2],block[0][3]);
		System.out.println("├─────┼─────┼─────┼─────┤");
		System.out.printf("│%5d│%5d│%5d│%5d│\n",block[1][0],block[1][1],block[1][2],block[1][3]);
		System.out.println("├─────┼─────┼─────┼─────┤");
		System.out.printf("│%5d│%5d│%5d│%5d│\n",block[2][0],block[2][1],block[2][2],block[2][3]);
		System.out.println("├─────┼─────┼─────┼─────┤");
		System.out.printf("│%5d│%5d│%5d│%5d│\n",block[3][0],block[3][1],block[3][2],block[3][3]);
		System.out.println("└─────┴─────┴─────┴─────┘");
	}
	
	public static void exit_graphic() {
		
		for(int i=0; i<3; i++) {
			try {
				Thread.sleep(1000); 	// 1초씩 쉰다
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf(".");
		}	
	}
	
	public static void b_random(int total) {
		Random r = new Random();	// Random 생성자
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
		if(count_empty() == 0) {	// 주의! 플레이어가 2048을 만들었다 하더라도, 빈공간이 없다면 게임 오버다.
			return 0;				// 따라서 0을 먼저 검사하는 조건문을 높은 우선순위로 둔다.
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
		int offs1=0, offs2=1;	// block[][]에 있는 variable들끼리 accumulate, gravitate하는데 사용할 offsets
		
		/* accumulation */
		for(int k=0; k<4; k++) {		
			for(offs1=0; offs1<3; offs1++) {
				for(offs2=offs1+1; offs2<4; ) {
					if(block[offs1][k] == block[offs2][k]) {
						block[offs1][k] *= 2;
						block[offs2][k] = 0;
						
						offs1++;	// offs1은 for문의 증가문을 통해서 한번 더 증가, 최종적으로 2번 증가됨.
						break;
					}
					/* 아무리 현재 오프셋과 같은 크기의 블럭이 어딘가에 있다고 하더라도,
					 * 멀리 떨어져있으면서 주변 블럭이 같은 블럭이 아니라면 
					 * 장애물 역할을 함. */
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
					if(block[offs2][k] > 0) {		// offs1의 block이 0일 때 대입시킴
						block[offs1][k] = block[offs2][k];
						block[offs2][k] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void sum_down() {
		int offs1=3, offs2=2;	// block[][]에 있는 variable들끼리 accumulate, gravitate하는데 사용할 offsets
		
		/* accumulation */
		for(int k=3; k>=0; k--) {		
			for(offs1=3; offs1>=1; offs1--) {
				for(offs2=offs1-1; offs2>=0; ) {
					if(block[offs1][k] == block[offs2][k]) {
						block[offs1][k] *= 2;
						block[offs2][k] = 0;
						
						offs1--;	// offs1은 for문의 증가문을 통해서 한번 더 증가, 최종적으로 2번 증가됨.
						break;
					}
					/* 아무리 현재 오프셋과 같은 크기의 블럭이 어딘가에 있다고 하더라도,
					 * 멀리 떨어져있으면서 주변 블럭이 같은 블럭이 아니라면 
					 * 장애물 역할을 함. */
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
					if(block[offs2][k] > 0) {		// offs1의 block이 0일 때 대입시킴
						block[offs1][k] = block[offs2][k];
						block[offs2][k] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void sum_left() {
		int offs1=0, offs2=1;	// block[][]에 있는 variable들끼리 accumulate, gravitate하는데 사용할 offsets
		
		/* accumulation */
		for(int k=0; k<4; k++) {		
			for(offs1=0; offs1<3; offs1++) {
				for(offs2=offs1+1; offs2<4; ) {
					if(block[k][offs1] == block[k][offs2]) {
						block[k][offs1] *= 2;
						block[k][offs2] = 0;
						
						offs1++;	// offs1은 for문의 증가문을 통해서 한번 더 증가, 최종적으로 2번 증가됨.
						break;
					}
					/* 아무리 현재 오프셋과 같은 크기의 블럭이 어딘가에 있다고 하더라도,
					 * 멀리 떨어져있으면서 주변 블럭이 같은 블럭이 아니라면 
					 * 장애물 역할을 함. */
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
					if(block[k][offs2] > 0) {		// offs1의 block이 0일 때 대입시킴
						block[k][offs1] = block[k][offs2];
						block[k][offs2] = 0;
						break;
					}
				}
			}
		}
		
	}
	
	public static void sum_right() {
		int offs1=3, offs2=2;	// block[][]에 있는 variable들끼리 accumulate, gravitate하는데 사용할 offsets
		
		/* accumulation */
		for(int k=3; k>=0; k--) {		
			for(offs1=3; offs1>=1; offs1--) {
				for(offs2=offs1-1; offs2>=0; ) {
					if(block[k][offs1] == block[k][offs2]) {
						block[k][offs1] *= 2;
						block[k][offs2] = 0;
						
						offs1--;	// offs1은 for문의 증가문을 통해서 한번 더 증가, 최종적으로 2번 증가됨.
						break;
					}
					/* 아무리 현재 오프셋과 같은 크기의 블럭이 어딘가에 있다고 하더라도,
					 * 멀리 떨어져있으면서 주변 블럭이 같은 블럭이 아니라면 
					 * 장애물 역할을 함. */
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
					if(block[k][offs2] > 0) {		// offs1의 block이 0일 때 대입시킴
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
		
		System.out.println("CUI버전 게임2048을 시작합니다.");
		System.out.println("w키는 위, s키는 아래, a키는 왼쪽, d키는 오른쪽입니다.");
		System.out.println("\"->\"화살표 옆에 해당 문자를 입력하고 엔터를 눌러주세요~");
	
		b_random(count_empty());	// 랜덤으로 2나 4블록을 넣는다.
		
		while(win_fail() == 1) {		// 만약 모든 블록이 0이 아닐 때, 게임오버된다.
			b_random(count_empty());
			graphic();			// 블록화면을 출력한다.
			System.out.printf("-> ");
			Input = scan.next().charAt(0); 	// 키보드로 문자 입력 받음
			
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
			default :	// 잘못된 문자를 입력했을 때
				System.out.println("잘못된 문자를 입력하셨습니다.\n\"w,a,s,d\"중에서 다시 입력해주세요.");
			}
			
			if(win_fail() == 2) {
				System.out.println("2048을 달성하셨습니다. 축하합니다~\n게임을 계속하시겠습니까?(yes = 1, no = 2)");
				System.out.printf("-> ");
				inp = scan.nextInt();
				
				if(inp == 2) {
					System.out.println("게임을 종료합니다");
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
