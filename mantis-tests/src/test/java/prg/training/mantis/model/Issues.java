package prg.training.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by QA Lady on 5/21/2017.
 */
public class Issues extends ForwardingSet<IssuesData> {

    private Set<IssuesData> delegate;

    public Issues(Issues issues) {
        this.delegate = new HashSet<IssuesData>(issues.delegate);
    }

    public Issues() {
        this.delegate = new HashSet<IssuesData>();
    }

    public Issues(Collection<IssuesData> issues) {
        this.delegate = new HashSet<IssuesData>(issues);
    }

    @Override
    protected Set<IssuesData> delegate() {
        return this.delegate;
    }

    public Issues withAdded(IssuesData issue) {
        Issues issues = new Issues(this);
        issues.add(issue);
        return issues;
    }

    public Issues without(IssuesData issue) {
        Issues issues = new Issues(this);
        issues.remove(issue);
        return issues;
    }
}
