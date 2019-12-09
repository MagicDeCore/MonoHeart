import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './module-t.reducer';
import { IModuleT } from 'app/shared/model/module-t.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IModuleTDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ModuleTDetail extends React.Component<IModuleTDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { moduleTEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ModuleT [<b>{moduleTEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{moduleTEntity.name}</dd>
            <dt>Node T</dt>
            <dd>
              {moduleTEntity.nodeTS
                ? moduleTEntity.nodeTS.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === moduleTEntity.nodeTS.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/module-t" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/module-t/${moduleTEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ moduleT }: IRootState) => ({
  moduleTEntity: moduleT.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ModuleTDetail);
