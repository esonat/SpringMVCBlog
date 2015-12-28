package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.hibernate.PostDaoHibernate;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.UserService;
import com.sonat.blog.service.impl.CategoryServiceImpl;
import com.sonat.blog.service.impl.UserServiceImpl;
 
@Configuration
public class PostDaoTestConfig {
    @Bean
    PostDao postDao(){
        return new PostDaoHibernate();
    } 
    @Bean
    UserService userService(){
        return new UserServiceImpl();
    }
    @Bean
    CategoryService categoryService(){
        return new CategoryServiceImpl();
    }
}

