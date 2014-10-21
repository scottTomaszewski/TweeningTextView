package com.kokalabs.svg;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

class SvgPathParser {
    private static final Set<Character> COMMANDS = Sets.newHashSet(
            'M', 'm', 'L', 'l', 'H', 'h', 'V', 'v', 'Z', 'z', 'C', 'c', 'S', 's', 'Q', 'q', 'T', 't');
    private static final Set<Character> SUPPORTED = Sets.newHashSet(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', ',', '-', '.');
    private static final Splitter COMMAND_ARG_SEPARATOR = Splitter.on(' ').omitEmptyStrings().trimResults();

    private final String source;

    public SvgPathParser(String pathDescriptions) {
        this.source = pathDescriptions;
    }

    public void parseUsing(SvgCommandHandler strategy) {
        List<String> commands = breakDown(source);
        for (String command : commands) {
            // ignore command character
            List<Float> args = doubleArgsOf(command.substring(1));
            switch (command.charAt(0)) {
                case 'M':
                    strategy.process_M(args.get(0), args.get(1));
                    break;
                case 'm':
                    strategy.process_m(args.get(0), args.get(1));
                    break;
                case 'L':
                    strategy.process_L(args.get(0), args.get(1));
                    break;
                case 'l':
                    strategy.process_l(args.get(0), args.get(1));
                    break;
                case 'H':
                    strategy.process_H(args.get(0));
                    break;
                case 'h':
                    strategy.process_h(args.get(0));
                    break;
                case 'V':
                    strategy.process_V(args.get(0));
                    break;
                case 'v':
                    strategy.process_v(args.get(0));
                    break;
                case 'z':
                case 'Z':
                    strategy.process_Z();
                    break;
                case 'C':
                    strategy.process_C(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
                    break;
                case 'c':
                    strategy.process_c(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
                    break;
                case 'S':
                    strategy.process_S(args.get(0), args.get(1), args.get(2), args.get(3));
                    break;
                case 's':
                    strategy.process_s(args.get(0), args.get(1), args.get(2), args.get(3));
                    break;
                case 'Q':
                    strategy.process_Q(args.get(0), args.get(1), args.get(2), args.get(3));
                    break;
                case 'q':
                    strategy.process_q(args.get(0), args.get(1), args.get(2), args.get(3));
                    break;
                case 'T':
                    strategy.process_T(args.get(0), args.get(1));
                    break;
                case 't':
                    strategy.process_t(args.get(0), args.get(1));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported character: " + command.charAt(0));
            }
        }
    }

    private List<Float> doubleArgsOf(String substring) {
        List<String> strings = COMMAND_ARG_SEPARATOR.splitToList(substring);
        List<Float> args = Lists.newArrayList();
        for (String arg : strings) {
            args.add(Float.parseFloat(arg) * 2);
        }
        return args;
    }

    private List<String> breakDown(String source) {
        List<String> commands = Lists.newArrayList();
        String tail = source;
        while (!tail.isEmpty()) {
            if (!COMMANDS.contains(tail.charAt(0))) {
                throw new IllegalArgumentException("Unsupported command: " + tail.charAt(0));
            }
            int nextCommandIndex = indexOfNextCommandOrNegative(tail);
            if (nextCommandIndex < 0) {
                // no more commands
                commands.add(tail.substring(0, tail.length()));
                break;
            }
            commands.add(tail.substring(0, nextCommandIndex));
            tail = tail.substring(nextCommandIndex);
        }
        return commands;
    }

    private int indexOfNextCommandOrNegative(String tail) {
        for (int i = 1; i < tail.length(); i++) {
            char maybe = tail.charAt(i);
            if (COMMANDS.contains(maybe)) {
                return i;
            } else if (!SUPPORTED.contains(maybe)) {
                throw new IllegalArgumentException("Unsupported character: " + maybe);
            }
        }
        return -1;
    }
}