import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 김소중 {
	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("./res/input.txt"));
		김소중 s = new 김소중();
		s.init();
		System.out.println(s.sb.toString());
	}
	
	StringBuilder sb = new StringBuilder();
	int[][] dels = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	Deque<int[]>[] ques;				// 세포 좌표를 담을 Deque 배열 (Deque[0] = 1 크기의 세포들 좌표)
	Map<Integer, List<Integer>> map;	// 세포(활성, 비활성, 죽은) 모두의 좌표 저장 => 이미 있는 좌표는 번식 불가
	int[] tempCell; 					// 활성화 된 세포들의 개수 => 일정 시간 후에 삭제되야 할 세포 들
	int k, count;
	
	
	public void fnc(int time) {
		int[] nums = {11,10,9,8,7,6,5,4,3,2};	// 세포 번식 타이밍 (세포 크기 10~1), 크기가 큰 세포 부터 번식
		
		for (int num : nums) {
			if (time < num) continue;
			if (time % num == 0) {	// ques[num-2]가 번식 해야됨 (num이 11이면 10크기 세포(ques[9])가 번식)
				if (ques[num-2] == null) continue;
				
				int size = ques[num-2].size();
				tempCell[num-2] = size;
				while (size-- > 0) {
					int[] cell = ques[num-2].pollFirst();	// cell들 저장해야 됨
					culture(cell, num-2);
				}
				
			}
		}
		for (int num : nums) {
			if (time < (num-1)*2) continue;
			if ((time+2) % num == 0) {
				tempCell[num-2] = 0;
			}
		}
		
		if (time++ < k) fnc(time);
	}
	
	public void culture(int[] cell, int num) {	// 세포 번식
		for (int[] del : dels) {
			int ny = cell[0] + del[0];
			int nx = cell[1] + del[1];
			
			if (map.containsKey(ny) && map.get(ny).contains(nx)) continue;
			ques[num].offer(new int[] {ny,nx});
			mapPut(ny, nx);
		}
	}
	
	
	public void init() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			
			int t = Integer.parseInt(br.readLine());
			for (int test_case = 1; test_case <= t; test_case++) {
				ques = new ArrayDeque[10];
				map = new HashMap<>();
				tempCell = new int[10];
				count = 0;
				
				input(br);
				fnc(1);
				counting();
				sb.append("#"+ test_case + " " + count + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void counting() {
		for (Deque<int[]> que : ques) {
			if (que == null) continue;
			count += que.size();
		}
		for (int temp : tempCell) {
			count += temp;
		}
	}
	
	public void mapPut(int key, int value) {
		if (!map.containsKey(key)) {
			map.put(key, new ArrayList<>());
		}
		map.get(key).add(value);
	}
	
	public void input(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		int n = Integer.parseInt(strs[0]);
		int m = Integer.parseInt(strs[1]);
		k = Integer.parseInt(strs[2]);

		for (int i = 0; i < n; i++) {
			strs = br.readLine().split(" ");
			for (int j = 0; j < m; j++) {
				int num = Integer.parseInt(strs[j]);
				if (num == 0)
					continue;

				if (ques[num - 1] == null) {
					ques[num - 1] = new ArrayDeque<>();
				}
				ques[num - 1].offerLast(new int[] { i, j });
				mapPut(i, j);
			}
		}
	}
}