package controller;

import entity.Blog;
import entity.Page;
import entity.User;
import entity.comment;
import service.findAllWithPageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mafx on 2019/4/25.
 */
public class FindAllWithPage extends HttpServlet {
    private findAllWithPageService daws=new findAllWithPageService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String flag=request.getParameter("flag");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
         switch (flag){
            case "list":
list(request,response);
break;
            case "login":
login(request,response);
break;
            case "checkDetail":
getBlogDetail(request,response);
            case "getBlogLitter":
getBlogLitter(request,response);
default:
login(request,response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }
    private void list(HttpServletRequest request, HttpServletResponse response){
        int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        int pageSize=11;
        int blogLevel=Integer.parseInt(request.getParameter("blogLevel"));/*0:���в��� 1����Ʒ���� 2�����Ų���*/
        String operClass=request.getParameter("operClass");
        if(!operClass.equals("getAllBlog")){
            User user=(User)(request.getSession().getAttribute("user"));
            blogLevel=user.getUserId();
        }
        findAllWithPageService ns=new findAllWithPageService();
        Page pg=ns.findAllNews(pageNum,pageSize,blogLevel,operClass);
        request.setAttribute("page",pg);
        try {
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void login(HttpServletRequest request, HttpServletResponse response){
        String username=request.getParameter("username");
        String pwd=request.getParameter("password");
        User user=new User();
        user=daws.getUserByUsnAndPwd(username,pwd);

        try {
            if(user!=null) {
                request.getSession().setAttribute("user",user);
                response.getWriter().print("<script language='javascript'>alert('��¼�ɹ�������');window.location.href='/index.jsp';</script>");
            }else{
                response.getWriter().print("<script language='javascript'>alert('�û�����������������µ�¼');history.back(-1);</script>");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void getBlogDetail(HttpServletRequest request, HttpServletResponse response){
        int id=Integer.parseInt(request.getParameter("id"));
        findAllWithPageService fs=new findAllWithPageService();
        Blog blog=new Blog();
        List<comment> commentsList=new ArrayList<comment>();
        commentsList=fs.getCommentById(id);
        blog=fs. getBlogDetailService(id);
        request.setAttribute("blog",blog);
        request.setAttribute("commentList",commentsList);
        try {
            request.getRequestDispatcher("/detail.jsp").forward(request, response);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void getBlogLitter(HttpServletRequest request, HttpServletResponse response){
        String litter=request.getParameter("litter");
        int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        int pageSize=11;
        int blogLevel=Integer.parseInt(request.getParameter("blogLevel"));/*0:���в��� 1����Ʒ���� 2�����Ų���*/
        String operClass=request.getParameter("operClass");
        if(!operClass.equals("getAllBlog")){
            User user=(User)(request.getSession().getAttribute("user"));
            blogLevel=user.getUserId();
        }
        findAllWithPageService ns=new findAllWithPageService();
        Page pg=ns.findAllNews(pageNum,pageSize,blogLevel,operClass,litter);
        request.setAttribute("page",pg);
        try {
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
