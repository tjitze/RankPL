package com.tr.rp.core;

import com.tr.rp.core.rankediterators.IteratorProvider;

/**
 * Interface for RPL statements. This interface extends
 * the IteratorProvider interface: every statement must
 * provide an iterator that implements its semantics.
 */
public interface DStatement extends IteratorProvider<VarStore>, LanguageElement {

}
