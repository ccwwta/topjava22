package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final AtomicInteger lastId = new AtomicInteger(7);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public InMemoryMealRepository() {
        MealsUtil.getMeals().forEach(meal -> meals.put(meal.getId(), meal));
    }

    @Override
    public void create(Meal mealTo) {
        log.debug("create {}", mealTo);
        int id = lastId.incrementAndGet();
        meals.put(id, new Meal(id, mealTo.getDateTime(), mealTo.getDescription(),
                mealTo.getCalories()));
    }

    @Override
    public void update(int id, Meal meal) {
        log.debug("update id {}, meal {}", id, meal);
        meals.put(id, meal);
    }

    @Override
    public void delete(int id) {
        log.debug("delete id {}", id);
        meals.remove(id);
    }

    @Override
    public Meal get(int id) {
        log.debug("get id {}", id);
        return meals.get(id);
    }

    @Override
    public List<Meal> findAll() {
        log.debug("findAll");
        return new ArrayList<>(meals.values());
    }

}
