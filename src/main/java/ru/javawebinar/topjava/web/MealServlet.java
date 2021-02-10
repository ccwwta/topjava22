package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    private MealRepository repository;

    @Override
    public void init() throws ServletException {
        log.debug("Servlet init()");
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        log.debug("doGet, action {}", action);

        action = (action == null) ? "" : action;

        switch (action) {
            case "update":
            case "new":
                req.setAttribute("meal", action.equals("update")
                        ? repository.get(Integer.parseInt(req.getParameter("id")))
                        : createNew());
                req.getRequestDispatcher("meal_card.jsp").forward(req, resp);
                return;
            case "delete":
                repository.delete(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("meals");
                return;
        }

        List<MealTo> mealTos = MealsUtil.filteredByStreams(
                repository.findAll(),
                LocalTime.MIN, LocalTime.MAX,
                MealsUtil.CALORIES_PER_DAY
        );

        req.setAttribute("meals", mealTos);
        req.setAttribute("dateFormatter", DATE_TIME_FORMATTER);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        log.debug("doPost {}", req.getParameterMap());

        Integer id = getIdOrNull(req);
        final LocalDateTime datetime = LocalDateTime.parse(req.getParameter("datetime"));
        final String description = req.getParameter("description");
        final int calories = Integer.parseInt(req.getParameter("calories"));

        Meal meal = new Meal(id, datetime, description, calories);
        if (id == null) {
            repository.create(meal);
        } else {
            repository.update(id, meal);
        }

        resp.sendRedirect("meals");
    }

    private Meal createNew() {
        return new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
    }


    private Integer getIdOrNull(HttpServletRequest req) {
        String par = req.getParameter("id");
        return par == null || par.isEmpty() ? null : Integer.parseInt(par);
    }
}
