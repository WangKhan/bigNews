package com.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.NewsDao;
import com.entity.News;

/**
 * Servlet implementation class NewsServlet
 */
@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NewsDao newsdao =new NewsDao();
	//指定绝对路径
	//String contentbasepath = "D:/BigNews/contentStore";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String news_id = req.getParameter("news_id");
		News news = newsdao.searchNews(news_id);
		//存新闻对象
		req.getSession().setAttribute("news", news);
		//拼接路径
		String contentpath = news.getContent();
		StringBuffer news_content = new StringBuffer();
		news_content = getSB(contentpath);
		//存新闻文本
		req.getSession().setAttribute("news_content", news_content);
		//跳转到指定的页面
		resp.sendRedirect("sources/single.jsp");
	}
	
	//读取文件
	public StringBuffer getSB(String filePath) {
		StringBuffer sb = new StringBuffer();
		Reader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(filePath);
			br = new BufferedReader(reader);
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb;
	}
}

