import type { SectorTreeDto } from "./types";

interface Props {
    node: SectorTreeDto;
    selected: number[];
    onToggle: (id: number) => void;
    level?: number;
}

export default function SectorNode({
                                       node,
                                       selected,
                                       onToggle,
                                       level = 0
                                   }: Props) {
    return (
        <div style={{ marginLeft: level * 16 }}>
            <label>
                <input
                    type="checkbox"
                    checked={selected.includes(node.id)}
                    onChange={() => onToggle(node.id)}
                />
                {" "}{node.name}
            </label>

            {node.children?.map(child =>
                <SectorNode
                    key={child.id}
                    node={child}
                    selected={selected}
                    onToggle={onToggle}
                    level={level + 1}
                />
            )}
        </div>
    );
}
