package com.hantsylabs.restexample.springmvc.test.jbehave;

import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.CommentRepository;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.io.*;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterControls;

import java.util.Arrays;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import static org.jbehave.core.reporters.Format.*;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public abstract class AbstractSpringJBehaveStory extends JUnitStory {

    @Inject
    PostRepository posts;

    @Inject
    CommentRepository comments;

    @Inject
    UserRepository users;

    @Inject
    PasswordEncoder passwordEncoder;

    public void clearData() {
        log.debug("clearing data...");

        comments.deleteAllInBatch();
        posts.deleteAllInBatch();
        users.deleteAllInBatch();
    }

    public void initData() {

        users.save(
                User.builder()
                .username("admin")
                .password(passwordEncoder.encode("test123"))
                .name("Administrator")
                .role("ADMIN")
                .build()
        );
    }

    private static final String STORY_TIMEOUT_IN_SECONDS = "120";

    public AbstractSpringJBehaveStory() {
        Embedder embedder = new Embedder();
        embedder.useEmbedderControls(embedderControls());
        embedder.useMetaFilters(Arrays.asList("-skip"));
        useEmbedder(embedder);
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryPathResolver(storyPathResolver())
                .useStoryLoader(storyLoader())
                .useStoryReporterBuilder(storyReporterBuilder())
                .useParameterControls(parameterControls());
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), getSteps());
    }

    private EmbedderControls embedderControls() {
        return new EmbedderControls()
                .doIgnoreFailureInView(false)
                .doIgnoreFailureInStories(false)
                .doVerboseFailures(true)
                .doVerboseFiltering(true)
                .useStoryTimeouts(STORY_TIMEOUT_IN_SECONDS);
    }

    private ParameterControls parameterControls() {
        return new ParameterControls()
                .useDelimiterNamedParameters(true);
    }

    private StoryPathResolver storyPathResolver() {
        return new UnderscoredCamelCaseResolver();
    }

    private StoryLoader storyLoader() {
        return new LoadFromClasspath();
    }

    private StoryReporterBuilder storyReporterBuilder() {
        return new StoryReporterBuilder()
                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                .withPathResolver(new ResolveToPackagedName())
                .withFailureTrace(true)
                .withDefaultFormats()
                .withFormats(IDE_CONSOLE, TXT, HTML);
    }

    public abstract Object[] getSteps();
}
