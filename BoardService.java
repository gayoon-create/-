package net.mbcac.board;

import java.util.List;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardService 
{
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public BoardService(HttpServletRequest request, HttpServletResponse response)
	{
		this.request=request;
		this.response=response;
	}
	
	public String process()
	{
		String cmd= request.getParameter("cmd");
		
		if(cmd.equals("list"))
		{
			BoardDAO dao = new BoardDAO();
			List<BoardVO> list = dao.getList();
			request.setAttribute("list", list);
			return "/jsp/board/boardList.jsp";
		}
		else if(cmd.equals("addForm"))
		{
			return "/jsp/board/addBoard.jsp";
		}
		else if(cmd.equals("add"))
		{
			BoardVO board = new BoardVO();
			
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String contents = request.getParameter("contents");

			board.setTitle(title);
			board.setAuthor(author);
			board.setContents(contents);
			
			BoardDAO dao = new BoardDAO();
			boolean added = dao.addForm(board);
			sendJSON("added", added);
		}
		else if(cmd.equals("detail"))
		{
			String sBnum = request.getParameter("bnum");
			int bnum = Integer.parseInt(sBnum);
			
			BoardDAO dao = new BoardDAO();
			BoardVO detail = dao.byBnum(bnum);
			request.setAttribute("detail", detail);
			return "/jsp/board/boardDetail.jsp";
		}
		else if(cmd.equals("delete"))
		{
			String sBnum = request.getParameter("bnum");
			int bnum = Integer.parseInt(sBnum);
			
			BoardVO key = new BoardVO();
			key.setBnum(bnum);
			BoardDAO dao = new BoardDAO();
			boolean deleted = dao.deleteBoard(key);
			sendJSON("deleted",deleted);
		}
		else if(cmd.equals("edit"))
		{
			String sBnum = request.getParameter("bnum");
			int bnum = Integer.parseInt(sBnum);
			
			BoardDAO dao = new BoardDAO();
			BoardVO edit = dao.byBnum(bnum);
			request.setAttribute("edit", edit);
			return "/jsp/board/boardEdit.jsp";
		}
		else if(cmd.equals("update"))
		{
			String sBnum = request.getParameter("bnum");
			int bnum = Integer.parseInt(sBnum);
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			
			BoardVO key = new BoardVO();
			key.setBnum(bnum);
			key.setTitle(title);
			key.setContents(contents);
			
			BoardDAO dao = new BoardDAO();
			boolean updated = dao.updateBoard(key);
			sendJSON("updated", updated);
		}
		return null;
	}

	private void sendJSON(String key, boolean value) 
	{
		JSONObject jsobj = new JSONObject();
		jsobj.put(key, value);
		
		try 
		{
			PrintWriter out = response.getWriter();
			out.print(jsobj.toJSONString());
			out.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	private BoardVO getParam() 
	{
		String sBnum = request.getParameter("bnum");
			int bnum = Integer.parseInt(sBnum);
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String sdate = request.getParameter("rdate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date rdate=null;
			try 
			{
				java.util.Date udate = sdf.parse(sdate);
				rdate = new java.sql.Date(udate.getTime());
			} 
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String contents = request.getParameter("contents");
		String Hits=request.getParameter("hits");
		int hits = Integer.parseInt(Hits);
		
		BoardVO board = new BoardVO();
		board.setBnum(bnum);
		board.setTitle(title);
		board.setAuthor(author);
		board.setRdate(rdate);
		board.setContents(contents);
		board.setHits(hits);
		
		return board;
	}
}
