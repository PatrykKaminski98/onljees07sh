package pl.coderslab.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.car.Car;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDao bookDao;
    private final PublisherDao publisherDao;

    public BookController(BookDao bookDao, PublisherDao publisherDao) {
        this.bookDao = bookDao;
        this.publisherDao = publisherDao;
    }

    @ResponseBody
    @GetMapping("/save")
    public String save(){
        Book book = new Book();
        book.setTitle("Thinking in Java");

        Publisher build =
                Publisher.builder().name("Helion").city("Warszawa").zipCode("09-400").street("Płocka").number("11B").build();

        publisherDao.save(build);
        book.setPublisher(build);
        bookDao.save(book);
        return "ok";
    }

    @GetMapping("/add")
    public String carForm(Model model) {
        model.addAttribute("publishers", publisherDao.findAll());
        model.addAttribute("book", new Book());
        return "book/add";
    }

    @PostMapping("/add")
    public String bookAdd(Book book) {
       bookDao.save(book);
        return "redirect:/book/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("books", bookDao.findAll());
        return "book/list";
    }


}
