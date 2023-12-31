import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles = new ArrayList<Article>();
	static List<Membership> memberships = new ArrayList<Membership>();
	public static void main(String[] args) {

		System.out.println("== 프로그램 시작 ==");

		makeTestData();

		Scanner sc = new Scanner(System.in);
		int lastArticleId = 3;

		while (true) {

			System.out.printf("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("너 명령어 입력 안했어");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}

			if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
				} else {
					System.out.println("번호      /    제목     /    조회   ");
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						System.out.printf(" %4d     /   %5s    /      %4d  \n", article.id, article.title, article.hit);
						if(command.startsWith("article list search ")) {
							String[] list_title=command.split(" ");
							if(list_title[4]==article.title) {
								System.out.println(article.id+  "/" +article.title+"/"+article.hit);

							}

						}
					}
				}

			} else if (command.equals("article write")) {
				int id = lastArticleId + 1;
				String regDate = Util.getNow();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번글이 생성되었습니다.\n", id);
				lastArticleId++;
			} else if (command.startsWith("article detail")) {

				String[] commandDiv = command.split(" "); // article detail 1

				int id = Integer.parseInt(commandDiv[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 없어\n", id);
					continue;
				}

				foundArticle.hit++;

				System.out.println("번호 : " + foundArticle.id);
				System.out.println("작성날짜 : " + foundArticle.regDate);
				System.out.println("수정날짜 : " + foundArticle.updateDate);
				System.out.println("제목 : " + foundArticle.title);
				System.out.println("내용 : " + foundArticle.body);
				System.out.println("조회수 : " + foundArticle.hit);

			} else if (command.startsWith("article modify")) {

				String[] commandDiv = command.split(" ");

				int id = Integer.parseInt(commandDiv[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 없어\n", id);
					continue;
				}

				System.out.printf("제목 : ");
				String newTitle = sc.nextLine();
				System.out.printf("내용 : ");
				String newBody = sc.nextLine();

				String updateDate = Util.getNow();
				foundArticle.title = newTitle;
				foundArticle.body = newBody;
				foundArticle.updateDate = updateDate;

			} else if (command.startsWith("article delete")) {

				String[] commandDiv = command.split(" "); // article delete 1

				int id = Integer.parseInt(commandDiv[2]);

				int foundIndex = -1;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundIndex = i;
						break;
					}
				}

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 없어\n", id);
					continue;
				}

				articles.remove(foundIndex);
				System.out.println(id + "번 글을 삭제했어");

			} else if(command.equals("member join")) {
				String loginId, loginPw, regDate, Name, RePw;

				System.out.printf("ID를 입력해주세요.");
				loginId=sc.nextLine();
				System.out.printf("PW를 입력해주세요.");
				loginPw=sc.nextLine();
				System.out.printf("PW를 확인해주세요");
				String RePW=sc.nextLine();
				System.out.printf("이름를 입력해주세요.");
				Name=sc.nextLine();
				regDate = Util.getNow();
				Membership membership = new Membership(loginId, loginPw, regDate, Name);
				memberships.add(membership);
				for(int i=0; i<memberships.size(); i++) {
					if(membership.loginId==null) {
						System.out.println("이 아이디는 사용할수 있습니다.");
						break;
					} else if(membership.loginId==loginId){
						System.out.println("이 아이디는 사용하실 수 있습니다.");
						break;
					} else {
						System.out.println("이미 사용중인 아이디입니다..");
						break;
					}
				}
				System.out.println("회원가입이 완료되었습니다.");


			} else {
				System.out.println("존재하지 않는 명령어입니다");
				continue;
			}

		}
		System.out.println("== 프로그램 종료 ==");

		sc.close();
	}
	private static int getArticleIndexById(int id) {

		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.id == id) {
				return i;
			}
		}

		return -1;
	}

	private static Article getArticleById(int id) {

		//		for (int i = 0; i < articles.size(); i++) {
		//			Article article = articles.get(i);
		//			if (article.id == id) {
		//				return article;
		//			}
		//		}

		//		for (Article article : articles) {
		//			if (article.id == id) {
		//				return article;
		//			}
		//		}

		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}
	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터 3개 생성 완료");
		articles.add(new Article(1, Util.getNow(), Util.getNow(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNow(), Util.getNow(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNow(), Util.getNow(), "제목3", "내용3", 33));
	}
}
class Membership {
	String loginId;
	String loginPw;
	String Name;
	String regDate;

	Membership(String loginId, String loginPw, String Name, String regDate){
		this.loginId=loginId;
		this.loginPw=loginPw;
		this.Name=Name;
		this.regDate=regDate;

	}
}
class Article {
	int id;
	String regDate;
	String updateDate;
	String title;
	String body;
	int hit;

	Article(int id, String regDate, String updateDate, String title, String body) {
		this(id, regDate, updateDate, title, body, 0);
	}

	Article(int id, String regDate, String updateDate, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}
}